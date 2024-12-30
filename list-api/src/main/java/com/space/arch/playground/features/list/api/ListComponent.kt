package com.space.arch.playground.features.list.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.arch.core.FeatureComponent
import com.space.arch.playground.repository.api.ListItem

interface ListComponent : FeatureComponent {
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
