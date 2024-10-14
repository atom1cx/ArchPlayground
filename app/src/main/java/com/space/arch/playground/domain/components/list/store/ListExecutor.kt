package com.space.arch.playground.domain.components.list.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.space.arch.playground.domain.components.list.store.ListStore.Label
import com.space.arch.playground.domain.components.list.store.ListStore.Message
import com.space.arch.playground.domain.components.list.store.ListStore.State

class ListExecutor : CoroutineExecutor<Unit, ListStore.Action, State, Message, Label>() {
    override fun executeAction(action: ListStore.Action) {
        when (action) {
            is ListStore.Action.NewItemsReceived -> {
                dispatch(
                    Message.UpdateItems(
                        action.items
                    )
                )
            }
        }
    }
}
