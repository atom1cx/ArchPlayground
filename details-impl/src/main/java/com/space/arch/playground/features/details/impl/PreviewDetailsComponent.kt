package com.space.arch.playground.features.details.impl

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.features.details.api.DetailsComponent
import com.space.arch.playground.repository.api.ItemType
import com.space.arch.playground.repository.api.ListItem

class PreviewDetailsComponent : DetailsComponent {
    override val model: Value<DetailsComponent.Model> = MutableValue(
        DetailsComponent.Model.Data(
            data = ListItem(
                id = 0L,
                title = "Title",
                subtitle = "Subtitle",
                pictureUrl = "",
                type = ItemType.FIRST
            )
        )
    )

    override fun onTryAgain() {}

    override fun onBackPressed() {}
}