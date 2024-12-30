package com.space.arch.playground.features.list.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.space.arch.playground.arch.util.stateAsValue
import com.space.arch.playground.features.list.api.ListComponent
import com.space.arch.playground.features.list.impl.store.ListStoreFactory
import com.space.arch.playground.repository.api.ListItem

class DefaultListComponent(
    componentContext: ComponentContext,
    private val storeFactory: ListStoreFactory,
    private val onItemClicked: (Long) -> Unit,
    private val onNewItemClicked: () -> Unit
) : ListComponent,
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    override val model: Value<List<ListItem>> = store.stateAsValue().map { it.items }

    override fun itemClicked(id: Long) = onItemClicked(id)

    override fun createNewItem() = onNewItemClicked()

    class FactoryImpl(
        private val storeFactory: ListStoreFactory
    ) : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onItemClicked: (id: Long) -> Unit,
            onNewItemClicked: () -> Unit
        ): ListComponent {
            return DefaultListComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                onItemClicked = onItemClicked,
                onNewItemClicked = onNewItemClicked
            )
        }
    }
}
