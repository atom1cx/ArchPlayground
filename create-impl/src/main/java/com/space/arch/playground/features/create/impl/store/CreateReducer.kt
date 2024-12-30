package com.space.arch.playground.features.create.impl.store

import com.arkivanov.mvikotlin.core.store.Reducer

object CreateReducer : Reducer<CreateStore.State, CreateStore.Message> {
    override fun CreateStore.State.reduce(msg: CreateStore.Message): CreateStore.State {
        return when (msg) {
            is CreateStore.Message.ChangeCanSave -> {
                copy(canSave = msg.canSave)
            }
            is CreateStore.Message.Loading -> {
                copy(processing = msg.loading)
            }
            is CreateStore.Message.SubtitleChanged -> {
                copy(subtitle = msg.subtitle)
            }
            is CreateStore.Message.TitleChanged -> {
                copy(title = msg.title)
            }
            is CreateStore.Message.TypeChanged -> {
                copy(checkedType = msg.checkedType)
            }
        }
    }
}