package com.space.arch.playground.domain.components.secondfeature

import com.arkivanov.decompose.ComponentContext
import com.space.arch.playground.domain.model.ListItem
import kotlinx.coroutines.flow.StateFlow

class DefaultSecondFeatureComponent(
    componentContext: ComponentContext,
    onFinished: () -> Unit
) : SecondFeatureComponent,
    ComponentContext by componentContext {
    /*
    private val store = instanceKeeper.getStore {
        SecondFeatureStoreFactory(DefaultStoreFactory()).create()
    }
    override val labels: Flow<Label> = store.labels
    override val model: StateFlow<Model> = TODO()

         */
    override val model: StateFlow<ListItem>
        get() = TODO("Not yet implemented")
}
