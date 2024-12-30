package com.space.arch.playground.repository.impl.di

import com.space.arch.playground.repository.api.ItemsRepository
import com.space.arch.playground.repository.impl.ItemsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {
    single<ItemsRepository> {
        ItemsRepositoryImpl(
            ioDispatcher = Dispatchers.IO
        )
    }
}