package com.space.arch.playground.features.create.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.arch.core.FeatureComponent

interface CreateComponent : FeatureComponent {
    val model: Value<Model>

    val event: Value<Event>

    data class Model(
        val title: String,
        val subtitle: String,
        val typeChecked: Boolean,
        val canSave: Boolean,
        val loading: Boolean
    )

    sealed interface Event {
        data class Error(val text: String) : Event
        data object Skip : Event
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit
        ): CreateComponent
    }

    fun onTitleChanged(title: String)

    fun onSubtitleChanged(subtitle: String)

    fun onTypeSwitched(switched: Boolean)

    fun onSaveClicked()

    fun onBackPressed()
}