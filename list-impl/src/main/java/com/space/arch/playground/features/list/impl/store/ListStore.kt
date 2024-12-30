package com.space.arch.playground.features.list.impl.store

import com.arkivanov.mvikotlin.core.store.Store
import com.space.arch.playground.repository.api.ListItem

interface ListStore : Store<Nothing, ListStore.State, ListStore.Label> {
    data class State(
        val items: List<ListItem> = listOf()
    )

    sealed interface Action {
        data class NewItemsReceived(
            val items: List<ListItem>
        ) : Action
    }

    sealed interface Message {
        data class UpdateItems(val items: List<ListItem>) : Message
    }

    sealed interface Label
}
