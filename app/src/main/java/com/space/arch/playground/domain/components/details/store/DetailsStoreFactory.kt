package com.space.arch.playground.domain.components.details.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.space.arch.playground.domain.components.details.store.DetailsStore.Intent
import com.space.arch.playground.domain.components.details.store.DetailsStore.Label
import com.space.arch.playground.domain.components.details.store.DetailsStore.State
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: ItemsRepository
) {

    fun create(id: Long): DetailsStore {
        return DetailsStoreImpl(id)
    }

    private inner class DetailsStoreImpl(
        private val id: Long
    ) :
        DetailsStore,
        Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State.Loading,
            bootstrapper = DetailsBootstrapper(
                id = id,
                repository = repository
            ),
            executorFactory = {
                DetailsExecutor(
                    id = id,
                    repository = repository
                )
            },
            reducer = DetailsReducer
        )

    private inner class DetailsBootstrapper(
        private val id: Long,
        private val repository: ItemsRepository
    ) : CoroutineBootstrapper<DetailsStore.Action>() {
        override fun invoke() {
            scope.launch {
                repository.getItem(id)
                    .flowOn(Dispatchers.Default)
                    .catch {
                        dispatch(DetailsStore.Action.PostLoadFailed(it))
                    }
                    .collect { dispatch(DetailsStore.Action.DataLoaded(it)) }
            }

        }
    }
}
