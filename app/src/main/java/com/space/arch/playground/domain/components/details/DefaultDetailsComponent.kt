package com.space.arch.playground.domain.components.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.space.arch.playground.domain.components.details.store.DetailsStore
import com.space.arch.playground.domain.components.details.store.DetailsStoreFactory
import com.space.arch.playground.util.stateAsValue

class DefaultDetailsComponent(
    componentContext: ComponentContext,
    id: Long,
    storeFactory: DetailsStoreFactory,
    private val onFinished: () -> Unit
) : DetailsComponent,
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        storeFactory.create(
            id
        )
    }

    override val model: Value<DetailsComponent.Model> = store.stateAsValue().map {
        when (it) {
            is DetailsStore.State.Error -> {
                DetailsComponent.Model.Error(
                    it.message
                )
            }

            DetailsStore.State.Loading -> {
                DetailsComponent.Model.Loading
            }

            is DetailsStore.State.Success -> {
                DetailsComponent.Model.Data(
                    it.data
                )
            }
        }
    }

    override fun onTryAgain() {
        store.accept(DetailsStore.Intent.TryAgain)
    }

    override fun onBackPressed() = onFinished()

    class FactoryImpl(
        private val storeFactory: DetailsStoreFactory
    ) : DetailsComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            id: Long,
            onFinished: () -> Unit
        ) = DefaultDetailsComponent(
            componentContext = componentContext,
            id = id,
            storeFactory = storeFactory,
            onFinished = onFinished
        )
    }
}
