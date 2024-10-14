package com.space.arch.playground.domain.components.secondfeature

import com.arkivanov.decompose.ComponentContext
import com.space.arch.playground.domain.model.ListItem
import kotlinx.coroutines.flow.StateFlow

interface SecondFeatureComponent {
    val model: StateFlow<ListItem>

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit
        ): SecondFeatureComponent
    }
}
