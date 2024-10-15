package com.space.arch.playground.domain.components.create.store

import com.arkivanov.mvikotlin.core.store.Store
import com.space.arch.playground.domain.components.create.store.CreateStore.Intent
import com.space.arch.playground.domain.components.create.store.CreateStore.Label
import com.space.arch.playground.domain.components.create.store.CreateStore.State

interface CreateStore : Store<Intent, State, Label> {
    data class State(
        val title: String = "",
        val subtitle: String = "",
        val pictureUrl: String = "",
        val canSave: Boolean = false,
        val processing: Boolean = false,
        val checkedType: Boolean = false
    )

    sealed interface Intent {
        data class ChangeTitle(val title: String) : Intent
        data class ChangeSubtitle(val subtitle: String) : Intent
        data class ChangeType(val checked: Boolean) : Intent
        data object Save : Intent
    }

    sealed interface Message {
        data class TitleChanged(val title: String) : Message
        data class SubtitleChanged(val subtitle: String) : Message
        data class TypeChanged(val checkedType: Boolean) : Message
        data class ChangeCanSave(val canSave: Boolean) : Message
        data class Loading(val loading: Boolean) : Message
    }

    sealed interface Label {
        data object Created : Label
        data class ShowError(val text: String) : Label
    }

}
