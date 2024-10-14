package com.space.arch.playground.domain.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.space.arch.playground.domain.components.create.CreateComponent
import com.space.arch.playground.domain.components.details.DetailsComponent
import com.space.arch.playground.domain.components.list.ListComponent
import com.space.arch.playground.domain.components.root.RootComponent.Child
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val listComponentFactory: ListComponent.Factory,
    private val detailsComponentFactory: DetailsComponent.Factory,
    private val createComponentFactory: CreateComponent.Factory,
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
                listComponent(
                    componentContext = childComponentContext
                )
            )

            is Config.Details -> Child.FirstFeature(
                detailsComponent(
                    componentContext = childComponentContext,
                    id = config.id
                )
            )

            is Config.Create -> Child.SecondFeature(
                createComponent(
                    componentContext = childComponentContext
                )
            )
        }

    private fun listComponent(componentContext: ComponentContext): ListComponent =
        listComponentFactory(
            componentContext = componentContext,
            onItemClicked = { id ->
                navigation.push(
                    Config.Details(
                        id
                    )
                )
            },
            onNewItemClicked = {
                navigation.push(
                    Config.Create
                )
            }
        )

    private fun detailsComponent(
        componentContext: ComponentContext,
        id: Long
    ): DetailsComponent =
        detailsComponentFactory(
            componentContext = componentContext,
            id = id,
            onFinished = navigation::pop,
        )

    private fun createComponent(componentContext: ComponentContext): CreateComponent =
        createComponentFactory(
            componentContext = componentContext,
            onFinished = navigation::pop,
        )

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    class FactoryImpl(
        private val listComponentFactory: ListComponent.Factory,
        private val detailsComponentFactory: DetailsComponent.Factory,
        private val createComponentFactory: CreateComponent.Factory
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return DefaultRootComponent(
                componentContext = componentContext,
                listComponentFactory = listComponentFactory,
                detailsComponentFactory = detailsComponentFactory,
                createComponentFactory = createComponentFactory
            )
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val id: Long) : Config

        @Serializable
        data object Create : Config
    }
}
