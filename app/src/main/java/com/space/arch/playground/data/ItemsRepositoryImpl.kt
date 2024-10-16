package com.space.arch.playground.data

import com.space.arch.playground.domain.model.ItemType
import com.space.arch.playground.domain.model.ListItem
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random

class ItemsRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher
) : ItemsRepository {
    private val _items = MutableStateFlow(
        List(5) {
            if (it % 2 == 0) {
                ListItem(
                    id = it.toLong(),
                    title = "Item $it",
                    subtitle = "Subtitle of item $it",
                    pictureUrl = "",
                    type = ItemType.FIRST
                )
            } else {
                ListItem(
                    id = it.toLong(),
                    title = "Item $it",
                    subtitle = "Subtitle of item $it",
                    pictureUrl = "",
                    type = ItemType.SECOND
                )
            }
        }
    )

    override fun getItems(): Flow<List<ListItem>> {
        return _items.asSharedFlow()
    }

    private val randomizer = Random(123)
    override fun getItem(itemId: Long): Flow<ListItem> {
        return _items.map {
            it.first { item -> item.id == itemId }
        }.onEach {
            delay(1000)
            if (randomizer.nextBoolean()) {
                throw IOException("Something went wrong. Please, try again")
            }
        }
    }

    override suspend fun addItem(item: ListItem) {
        withContext(ioDispatcher) {
            delay(1000)
            if (randomizer.nextBoolean()) {
                throw IOException("Something went wrong. Please, try again")
            }
            _items.update {
                it + item.copy(
                    id = (it.size + 1).toLong()
                )
            }
        }
    }
}