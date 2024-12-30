package com.space.arch.playground.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.domain.DefaultRootComponent
import com.space.arch.playground.domain.RootComponent
import com.space.arch.playground.features.create.impl.di.createModule
import com.space.arch.playground.features.details.impl.di.detailsModule
import com.space.arch.playground.features.list.impl.di.listModule
import com.space.arch.playground.repository.impl.di.repositoryModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val koin by lazy { initKoin().koin }

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(
        appModule,
        repositoryModule,
        listModule,
        createModule,
        detailsModule
    )
}

val appModule = module {
    single<StoreFactory> {
        //Use this only for debug, use DefaultStoreFactory instead
        LoggingStoreFactory(TimeTravelStoreFactory())
    }

    single<RootComponent.Factory> {
        DefaultRootComponent.FactoryImpl(
            listComponentFactory = get(),
            detailsComponentFactory = get(),
            createComponentFactory = get()
        )
    }

    single<FeatureContentFactory> {
        FeatureContentFactoryImpl()
    }
}