package com.space.arch.playground.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.space.arch.playground.data.ItemsRepositoryImpl
import com.space.arch.playground.domain.components.details.DefaultDetailsComponent
import com.space.arch.playground.domain.components.details.DetailsComponent
import com.space.arch.playground.domain.components.details.store.DetailsStoreFactory
import com.space.arch.playground.domain.components.list.DefaultListComponent
import com.space.arch.playground.domain.components.list.ListComponent
import com.space.arch.playground.domain.components.list.store.ListStoreFactory
import com.space.arch.playground.domain.components.root.DefaultRootComponent
import com.space.arch.playground.domain.components.root.RootComponent
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val koin by lazy { initKoin().koin }

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(appModule)
}

val appModule = module {
    // Components
    single<ListComponent.Factory> {
        DefaultListComponent.FactoryImpl(
            storeFactory = get(),
        )
    }

    single<DetailsComponent.Factory> {
        DefaultDetailsComponent.FactoryImpl(
            storeFactory = get()
        )
    }

    // Store factories
    single<StoreFactory> {
        //Use this only for debug, use DefaultStoreFactory instead
        LoggingStoreFactory(TimeTravelStoreFactory())
    }

    single<ListStoreFactory> {
        ListStoreFactory(
            storeFactory = get(),
            itemsRepository = get()
        )
    }

    single<DetailsStoreFactory> {
        DetailsStoreFactory(
            storeFactory = get(),
            repository = get()
        )
    }

    // Repositories
    single<ItemsRepository> {
        ItemsRepositoryImpl(
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
        )
    }

    // Root
    single<RootComponent.Factory> {
        DefaultRootComponent.FactoryImpl(
            listComponentFactory = get(),
            detailsComponentFactory = get(),
            createComponentFactory = get()
        )
    }
}