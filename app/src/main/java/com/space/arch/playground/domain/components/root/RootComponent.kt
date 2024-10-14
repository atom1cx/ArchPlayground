package com.space.arch.playground.domain.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.components.create.CreateComponent
import com.space.arch.playground.domain.components.details.DetailsComponent
import com.space.arch.playground.domain.components.list.ListComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class List(val component: ListComponent) : Child()
        class FirstFeature(val component: DetailsComponent) : Child()
        class SecondFeature(val component: CreateComponent) : Child()
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}