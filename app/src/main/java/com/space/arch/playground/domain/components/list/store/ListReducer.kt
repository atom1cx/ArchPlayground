package com.space.arch.playground.domain.components.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.space.arch.playground.domain.components.list.store.ListStore.Message
import com.space.arch.playground.domain.components.list.store.ListStore.State

object ListReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        return when (msg) {
            is Message.UpdateItems -> {
                copy(
                    items = msg.items
                )
            }
        }
    }
}