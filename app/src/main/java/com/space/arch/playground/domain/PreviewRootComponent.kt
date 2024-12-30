package com.space.arch.playground.domain

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.features.list.impl.PreviewListComponent

class PreviewRootComponent : RootComponent {
    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = RootComponent.Child.List(
                    PreviewListComponent()
                )
            )
        )

    override fun onBackClicked(toIndex: Int) {}
}