package com.space.arch.playground.features.list.impl.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.space.arch.playground.repository.api.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ListStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: ItemsRepository,
) {

    fun create(): ListStore {
        return ListStoreImpl()
    }

    private inner class ListStoreImpl :
        ListStore,
        Store<Nothing, ListStore.State, ListStore.Label> by storeFactory.create(
            name = "ListStore",
            initialState = ListStore.State(),
            bootstrapper = ListBootstrapper(
                itemsRepository = repository
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