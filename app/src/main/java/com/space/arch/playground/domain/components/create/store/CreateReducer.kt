package com.space.arch.playground.domain.components.create.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.space.arch.playground.domain.components.create.store.CreateStore.State
import com.space.arch.playground.domain.components.create.store.CreateStore.Message

object CreateReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        return when (msg) {
            is Message.ChangeCanSave -> {
                copy(canSave = msg.canSave)
            }
            is Message.Loading -> {
                copy(processing = msg.loading)
            }
            is Message.SubtitleChanged -> {
                copy(subtitle = msg.subtitle)
            }
            is Message.TitleChanged -> {
                copy(title = msg.title)
            }
            is Message.TypeChanged -> {
                copy(checkedType = msg.checkedType)
            }
        }
    }
}