package com.space.arch.playground.domain.components.details.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.space.arch.playground.domain.components.details.store.DetailsStore.Message
import com.space.arch.playground.domain.components.details.store.DetailsStore.State

object DetailsReducer : Reducer<State, Message> {
    override fun State.reduce(msg: Message): State {
        return when (msg) {
            is Message.ShowError -> {
                State.Error(msg.errorText)
            }

            is Message.UpdateData -> {
                State.Success(msg.data)
            }

            Message.Loading -> {
                State.Loading
            }
        }
    }
}