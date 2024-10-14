package com.space.arch.playground.domain.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.components.firstfeature.FirstFeatureComponent
import com.space.arch.playground.domain.components.list.ListComponent
import com.space.arch.playground.domain.components.secondfeature.SecondFeatureComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class List(val component: ListComponent) : Child()
        class FirstFeature(val component: FirstFeatureComponent) : Child()
        class SecondFeature(val component: SecondFeatureComponent) : Child()
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}