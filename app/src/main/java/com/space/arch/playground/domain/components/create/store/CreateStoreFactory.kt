package com.space.arch.playground.domain.components.create.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.space.arch.playground.domain.components.create.store.CreateStore.Intent
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.space.arch.playground.domain.components.create.store.CreateStore.Label
import com.space.arch.playground.domain.components.create.store.CreateStore.State
import com.space.arch.playground.domain.components.create.store.CreateStore.Message
import com.space.arch.playground.domain.components.details.store.DetailsStore
import com.space.arch.playground.domain.repositories.ItemsRepository

class CreateStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: ItemsRepository
) {

    fun create(): CreateStore {
        return CreateStoreImpl()
    }

    private inner class CreateStoreImpl :
        CreateStore,
        Store<Intent, State, Label> by storeFactory.create(
            name = "CreateStore",
            initialState = State(
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
