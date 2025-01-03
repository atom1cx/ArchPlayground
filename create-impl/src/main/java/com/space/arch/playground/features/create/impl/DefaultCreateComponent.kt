package com.space.arch.playground.features.create.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.space.arch.playground.arch.util.labelsAsValue
import com.space.arch.playground.arch.util.stateAsValue
import com.space.arch.playground.features.create.api.CreateComponent
import com.space.arch.playground.features.create.impl.store.CreateStore
import com.space.arch.playground.features.create.impl.store.CreateStoreFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DefaultCreateComponent(
    componentContext: ComponentContext,
    storeFactory: CreateStoreFactory,
    private val onFinished: () -> Unit
) : CreateComponent,
    ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        storeFactory.create()
    }

    init {
        coroutineScope().launch {
            store.labels.collectLatest { label ->
                when (label) {
                    CreateStore.Label.Created -> {
                        onFinished()
                    }

                    else -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    override val model: Value<CreateComponent.Model> = store.stateAsValue().map {
        CreateComponent.Model(
            title = it.title,
            subtitle = it.subtitle,
            typeChecked = it.checkedType,
            canSave = it.canSave,
            loading = it.processing
        )
    }

    override val event: Value<CreateComponent.Event> =
        store.labelsAsValue(CreateComponent.Event.Skip).map {
            when (it) {
                is CreateStore.Label.ShowError -> {
                    CreateComponent.Event.Error(it.text)
                }

                else -> {
                    CreateComponent.Event.Skip
                }
            }
        }

    override fun onTitleChanged(title: String) {
        store.accept(CreateStore.Intent.ChangeTitle(title))
    }

    override fun onSubtitleChanged(subtitle: String) {
        store.accept(CreateStore.Intent.ChangeSubtitle(subtitle))
    }

    override fun onTypeSwitched(switched: Boolean) {
        store.accept(CreateStore.Intent.ChangeType(switched))
    }

    override fun onSaveClicked() {
        store.accept(CreateStore.Intent.Save)
    }

    override fun onBackPressed() = onFinished()

    class FactoryImpl(
        private val storeFactory: CreateStoreFactory
    ) : CreateComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onFinished: () -> Unit
        ): CreateComponent {
            return DefaultCreateComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                onFinished = onFinished
            )
        }
    }
}
