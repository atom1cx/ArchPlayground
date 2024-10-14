package com.space.arch.playground.domain.components.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.model.ListItem

interface ListComponent {
    val model: Value<List<ListItem>>

    fun itemClicked(item: ListItem)

    fun createNewItem()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onItemClicked: (item: ListItem) -> Unit,
            onNewItemClicked: () -> Unit
        ): ListComponent
    }
}
