package com.space.arch.playground.repository.api

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(): Flow<List<ListItem>>
    fun getItem(itemId: Long): Flow<ListItem>
    suspend fun addItem(
        item: ListItem
    )
}