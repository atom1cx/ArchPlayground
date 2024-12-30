package com.space.arch.playground.features.details.impl.di

import com.space.arch.playground.features.details.api.DetailsComponent
import com.space.arch.playground.features.details.impl.DefaultDetailsComponent
import com.space.arch.playground.features.details.impl.store.DetailsStoreFactory
import org.koin.dsl.module

val detailsModule = module {
    single<DetailsComponent.Factory> {
        DefaultDetailsComponent.FactoryImpl(
            storeFactory = get()
        )
    }

    single<DetailsStoreFactory> {
        DetailsStoreFactory(
            storeFactory = get(),
            repository = get()
        )
    }
}