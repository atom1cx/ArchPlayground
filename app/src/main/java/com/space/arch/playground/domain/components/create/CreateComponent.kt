package com.space.arch.playground.domain.components.create

import com.arkivanov.decompose.ComponentContext
import com.space.arch.playground.domain.model.ListItem
import kotlinx.coroutines.flow.StateFlow

interface CreateComponent {
    val model: StateFlow<ListItem>

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit
        ): CreateComponent
    }
}
