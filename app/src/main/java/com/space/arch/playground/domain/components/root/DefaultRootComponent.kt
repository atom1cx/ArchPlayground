package com.space.arch.playground.domain.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.components.firstfeature.FirstFeatureComponent
import com.space.arch.playground.domain.components.list.ListComponent
import com.space.arch.playground.domain.components.root.RootComponent.Child
import com.space.arch.playground.domain.components.secondfeature.SecondFeatureComponent
import com.space.arch.playground.domain.model.ListItem
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val listComponentFactory: ListComponent.Factory,
    private val firstComponentFactory: FirstFeatureComponent.Factory,
    private val secondComponentFactory: SecondFeatureComponent.Factory,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child =
        when (config) {
            is Config.List -> Child.List(
                listComponent(childComponentContext)
            )

            is Config.FirstFeature -> Child.FirstFeature(
                firstFeatureComponent(childComponentContext)
            )

            is Config.SecondFeature -> Child.SecondFeature(
                secondFeatureComponent(
                    childComponentContext
                )
            )
        }

    private fun listComponent(componentContext: ComponentContext): ListComponent =
        listComponentFactory(
            componentContext = componentContext,
            onItemClicked = { item ->
                when (item) {
                    is ListItem.FirstItem -> {
                        navigation.push(
                            Config.FirstFeature(
                                item
                            )
                        )
                    }

                    is ListItem.SecondItem -> {
                        navigation.push(
                            Config.SecondFeature(
                                item
                            )
                        )
                    }
                }
            }
        )

    private fun firstFeatureComponent(componentContext: ComponentContext): FirstFeatureComponent =
        firstComponentFactory(
            componentContext = componentContext,
            onFinished = navigation::pop,
        )

    private fun secondFeatureComponent(componentContext: ComponentContext): SecondFeatureComponent =
        secondComponentFactory(
            componentContext = componentContext,
            onFinished = navigation::pop,
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    class Factory(
        private val listComponentFactory: ListComponent.Factory,
        private val firstComponentFactory: FirstFeatureComponent.Factory,
        private val secondComponentFactory: SecondFeatureComponent.Factory
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                listComponentFactory = listComponentFactory,
                firstComponentFactory = firstComponentFactory,
                secondComponentFactory = secondComponentFactory
            )
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class FirstFeature(val item: ListItem) : Config

        @Serializable
        data class SecondFeature(val id: ListItem) : Config
    }
}
