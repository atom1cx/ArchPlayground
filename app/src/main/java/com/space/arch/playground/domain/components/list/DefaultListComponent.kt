package com.space.arch.playground.domain.components.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.space.arch.playground.domain.components.list.store.ListStoreFactory
import com.space.arch.playground.domain.model.ListItem
import com.space.arch.playground.util.asValue

class DefaultListComponent(
    componentContext: ComponentContext,
    private val storeFactory: ListStoreFactory,
    private val onItemClicked: (ListItem) -> Unit,
    private val onNewItemClicked: () -> Unit
) : ListComponent,
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    override val model: Value<List<ListItem>> = store.asValue().map { it.items }

    override fun itemClicked(item: ListItem) = onItemClicked(item)

    override fun createNewItem() = onNewItemClicked()

    class FactoryImpl(
        private val storeFactory: ListStoreFactory
    ) : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onItemClicked: (item: ListItem) -> Unit,
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
