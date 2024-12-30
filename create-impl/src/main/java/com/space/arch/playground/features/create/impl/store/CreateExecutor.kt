package com.space.arch.playground.features.create.impl.store

import com.arkivanov.decompose.Cancellation
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.space.arch.playground.repository.api.ItemType
import com.space.arch.playground.repository.api.ItemsRepository
import com.space.arch.playground.repository.api.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateExecutor(
    private val repository: ItemsRepository
) : CoroutineExecutor<CreateStore.Intent, Unit, CreateStore.State, CreateStore.Message, CreateStore.Label>() {
    override fun executeIntent(intent: CreateStore.Intent) {
        when (intent) {
            is CreateStore.Intent.ChangeSubtitle -> {
                dispatch(
                    CreateStore.Message.SubtitleChanged(
                        intent.subtitle
                    )
                )
                dispatch(
                    CreateStore.Message.ChangeCanSave(
                        canSave()
                    )
                )
            }

            is CreateStore.Intent.ChangeTitle -> {
                dispatch(
                    CreateStore.Message.TitleChanged(
                        intent.title
                    )
                )
                dispatch(
                    CreateStore.Message.ChangeCanSave(
                        canSave()
                    )
                )
            }

            is CreateStore.Intent.ChangeType -> {
                dispatch(
                    CreateStore.Message.TypeChanged(
                        intent.checked
                    )
                )
            }

            CreateStore.Intent.Save -> {
                scope.launch {
                    val state = state()
                    try {
                        dispatch(
                            CreateStore.Message.Loading(true)
                        )
                        withContext(Dispatchers.Default) {
                            repository.addItem(
                                ListItem(
                                    id = 0,
                                    title = state.title,
                                    subtitle = state.subtitle,
                                    pictureUrl = state.pictureUrl,
                                    type = if (!state.checkedType) {
                                        ItemType.FIRST
                                    } else {
                                        ItemType.SECOND
                                    }
                                )
                            )
                        }
                        publish(CreateStore.Label.Created)
                    } catch (e: Throwable) {
                        if (e is Cancellation) throw e
                        publish(CreateStore.Label.ShowError(e.message ?: "Unknown error"))
                    } finally {
                        dispatch(CreateStore.Message.Loading(false))
                    }

                }
            }
        }
    }

    private fun canSave(): Boolean {
        return with(state()) {
            title.isNotEmpty() && subtitle.isNotEmpty()
        }
    }
}
