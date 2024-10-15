package com.space.arch.playground.domain.components.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface CreateComponent {
    val model: Value<Model>

    data class Model(
        val title: String,
        val subtitle: String,
        val typeChecked: Boolean,
        val canSave: Boolean,
        val loading: Boolean
    )

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onError: (String) -> Unit,
            onFinished: () -> Unit
        ): CreateComponent
    }

    fun onTitleChanged(title: String)

    fun onSubtitleChanged(subtitle: String)

    fun onTypeSwitched(switched: Boolean)

    fun onSaveClicked()

    fun onBackPressed()
}
