package com.space.arch.playground.features.details.impl.store

import com.arkivanov.mvikotlin.core.store.Reducer

object DetailsReducer : Reducer<DetailsStore.State, DetailsStore.Message> {
    override fun DetailsStore.State.reduce(msg: DetailsStore.Message): DetailsStore.State {
        return when (msg) {
            is DetailsStore.Message.ShowError -> {
                DetailsStore.State.Error(msg.errorText)
            }

            is DetailsStore.Message.UpdateData -> {
                DetailsStore.State.Success(msg.data)
            }

            DetailsStore.Message.Loading -> {
                DetailsStore.State.Loading
            }
        }
    }
}