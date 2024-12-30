package com.space.arch.playground.features.create.impl.di

import com.space.arch.playground.features.create.api.CreateComponent
import com.space.arch.playground.features.create.impl.DefaultCreateComponent
import com.space.arch.playground.features.create.impl.store.CreateStoreFactory
import org.koin.dsl.module

val createModule = module {
    single<CreateComponent.Factory> {
        DefaultCreateComponent.FactoryImpl(
            storeFactory = get()
        )
    }

    single<CreateStoreFactory> {
        CreateStoreFactory(
            storeFactory = get(),
            repository = get()
        )
    }
}