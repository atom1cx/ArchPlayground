package com.space.arch.playground.features.list.impl.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class ListExecutor :
    CoroutineExecutor<Unit, ListStore.Action, ListStore.State, ListStore.Message, ListStore.Label>() {
    override fun executeAction(action: ListStore.Action) {
        when (action) {
            is ListStore.Action.NewItemsReceived -> {
                dispatch(
                    ListStore.Message.UpdateItems(
                        action.items
                    )
                )
            }
        }
    }
}
