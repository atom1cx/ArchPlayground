package com.space.arch.playground.domain.components.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.space.arch.playground.domain.components.create.store.CreateStore
import com.space.arch.playground.domain.components.create.store.CreateStoreFactory
import com.space.arch.playground.util.asValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DefaultCreateComponent(
    componentContext: ComponentContext,
    storeFactory: CreateStoreFactory,
    private val onFinished: () -> Unit,
    private val onError: (String) -> Unit
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

                    is CreateStore.Label.ShowError -> {
                        onError(label.text)
                    }
                }
            }
        }
    }

    override val model: Value<CreateComponent.Model> = store.asValue().map {
        CreateComponent.Model(
            title = it.title,
            subtitle = it.subtitle,
            typeChecked = it.checkedType,
            canSave = it.canSave,
            loading = it.processing
        )
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
            onError: (String) -> Unit,
            onFinished: () -> Unit
        ): CreateComponent {
            return DefaultCreateComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                onFinished = onFinished,
                onError = onError
            )
        }
    }
}
