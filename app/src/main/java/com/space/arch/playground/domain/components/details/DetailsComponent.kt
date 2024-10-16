package com.space.arch.playground.domain.components.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.model.ListItem

interface DetailsComponent {
    val model: Value<Model>

    interface Model {
        data object Loading : Model
        data class Error(val text: String) : Model
        data class Data(val data: ListItem) : Model
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            id: Long,
            onFinished: () -> Unit
        ): DetailsComponent
    }

    fun onTryAgain()

    fun onBackPressed()
}
