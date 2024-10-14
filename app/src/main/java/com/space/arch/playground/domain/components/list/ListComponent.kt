package com.space.arch.playground.domain.components.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.model.ListItem

interface ListComponent {
    val model: Value<List<ListItem>>

    fun itemClicked(id: Long)

    fun createNewItem()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onItemClicked: (id: Long) -> Unit,
            onNewItemClicked: () -> Unit
        ): ListComponent
    }
}
