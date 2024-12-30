package com.space.arch.playground.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.features.create.api.CreateComponent
import com.space.arch.playground.features.details.api.DetailsComponent
import com.space.arch.playground.features.list.api.ListComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class List(val component: ListComponent) : Child()
        class Details(val component: DetailsComponent) : Child()
        class Create(val component: CreateComponent) : Child()
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}