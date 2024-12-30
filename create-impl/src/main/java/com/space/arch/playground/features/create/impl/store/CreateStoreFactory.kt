package com.space.arch.playground.features.create.impl.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.space.arch.playground.repository.api.ItemsRepository

class CreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: ItemsRepository
) {

    fun create(): CreateStore {
        return CreateStoreImpl()
    }

    private inner class CreateStoreImpl :
        CreateStore,
        Store<CreateStore.Intent, CreateStore.State, CreateStore.Label> by storeFactory.create(
            name = "CreateStore",
            initialState = CreateStore.State(
                title = "",
                subtitle = "",
                pictureUrl = "",
                canSave = false,
                processing = false
            ),
            executorFactory = { CreateExecutor(repository) },
            reducer = CreateReducer
        )
}
