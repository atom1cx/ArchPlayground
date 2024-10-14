package com.space.arch.playground.domain.components.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.space.arch.playground.domain.components.list.store.ListStore.Label
import com.space.arch.playground.domain.components.list.store.ListStore.State
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val itemsRepository: ItemsRepository,
) {

    fun create(): ListStore {
        return ListStoreImpl()
    }

    private inner class ListStoreImpl :
        ListStore,
        Store<Nothing, State, Label> by storeFactory.create(
            name = "ListStore",
            initialState = State(),
            bootstrapper = ListBootstrapper(
                itemsRepository = itemsRepository
            ),
            executorFactory = { ListExecutor() },
            reducer = ListReducer
        )

    private inner class ListBootstrapper(
        private val itemsRepository: ItemsRepository
    ) : CoroutineBootstrapper<ListStore.Action>() {
        override fun invoke() {
            scope.launch {
                itemsRepository
                    .getItems()
                    .flowOn(Dispatchers.Default)
                    .collect { items ->
                        dispatch(
                            ListStore.Action.NewItemsReceived(
                                items
                            )
                        )
                    }
            }
        }
    }
}