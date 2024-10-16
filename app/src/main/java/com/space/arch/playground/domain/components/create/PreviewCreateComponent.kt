package com.space.arch.playground.domain.components.create

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class PreviewCreateComponent : CreateComponent {
    override val model: Value<CreateComponent.Model> = MutableValue(
        CreateComponent.Model(
            title = "Title",
            subtitle = "Subtitle",
            typeChecked = true,
            canSave = true,
            loading = false
        )
    )
    override val event: Value<CreateComponent.Event> = MutableValue(
        CreateComponent.Event.Skip
    )

    override fun onTitleChanged(title: String) {}

    override fun onSubtitleChanged(subtitle: String) {}

    override fun onTypeSwitched(switched: Boolean) {}

    override fun onSaveClicked() {}

    override fun onBackPressed() {}
}
