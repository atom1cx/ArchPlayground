package com.space.arch.playground.domain.components.create

import com.arkivanov.decompose.ComponentContext
import com.space.arch.playground.domain.model.ListItem
import kotlinx.coroutines.flow.StateFlow

class DefaultCreateComponent(
    componentContext: ComponentContext,
    onFinished: () -> Unit
) : CreateComponent,
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
