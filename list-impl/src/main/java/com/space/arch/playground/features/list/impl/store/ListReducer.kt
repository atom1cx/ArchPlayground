package com.space.arch.playground.features.list.impl.store

import com.arkivanov.mvikotlin.core.store.Reducer

object ListReducer : Reducer<ListStore.State, ListStore.Message> {
    override fun ListStore.State.reduce(msg: ListStore.Message): ListStore.State {
        return when (msg) {
            is ListStore.Message.UpdateItems -> {
                copy(
                    items = msg.items
                )
            }
        }
    }
}