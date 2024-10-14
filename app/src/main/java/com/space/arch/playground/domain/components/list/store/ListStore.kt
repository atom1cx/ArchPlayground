package com.space.arch.playground.domain.components.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.space.arch.playground.domain.components.list.store.ListStore.Label
import com.space.arch.playground.domain.components.list.store.ListStore.State
import com.space.arch.playground.domain.model.ListItem

interface ListStore : Store<Nothing, State, Label> {
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
