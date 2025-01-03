package com.space.arch.playground.features.list.impl

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.features.list.api.ListComponent
import com.space.arch.playground.repository.api.ItemType
import com.space.arch.playground.repository.api.ListItem

class PreviewListComponent : ListComponent {
    override val model: Value<List<ListItem>> = MutableValue(
        listOf(
            ListItem(
                id = 0L,
                title = "Title",
                subtitle = "Subtitle",
                pictureUrl = "",
                type = ItemType.FIRST
            )
        )
    )

    override fun itemClicked(id: Long) {}

    override fun createNewItem() {}
}