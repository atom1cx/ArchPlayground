package com.space.arch.playground.domain.components.details.store

import com.arkivanov.mvikotlin.core.store.Store
import com.space.arch.playground.domain.components.details.store.DetailsStore.Intent
import com.space.arch.playground.domain.components.details.store.DetailsStore.Label
import com.space.arch.playground.domain.components.details.store.DetailsStore.State
import com.space.arch.playground.domain.model.ListItem

interface DetailsStore : Store<Intent, State, Label> {
    sealed interface State {
        data object Loading : State

        data class Success(val data: ListItem) : State

        data class Error(val message: String) : State
    }

    sealed interface Intent {
        data object TryAgain : Intent
    }

    sealed interface Message {
        data class UpdateData(val data: ListItem) : Message

        data class ShowError(val errorText: String) : Message

        data object Loading : Message
    }

    sealed interface Label

    sealed interface Action {
        data class DataLoaded(val data: ListItem) : Action

        data class PostLoadFailed(val throwable: Throwable) : Action
    }
}
