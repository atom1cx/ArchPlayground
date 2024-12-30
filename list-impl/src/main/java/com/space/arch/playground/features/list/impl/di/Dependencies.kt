package com.space.arch.playground.features.list.impl.di

import com.space.arch.playground.features.list.api.ListComponent
import com.space.arch.playground.features.list.impl.DefaultListComponent
import com.space.arch.playground.features.list.impl.store.ListStoreFactory
import org.koin.dsl.module

val listModule = module {
    single<ListComponent.Factory> {
        DefaultListComponent.FactoryImpl(
            storeFactory = get()
        )
    }

    single<ListStoreFactory> {
        ListStoreFactory(
            storeFactory = get(),
            repository = get()
        )
    }
}