package com.space.arch.playground.domain.repositories

import com.space.arch.playground.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(): Flow<List<ListItem>>
    fun getItem(itemId: Long): Flow<ListItem>
    suspend fun addItem(
        item: ListItem
    )
}