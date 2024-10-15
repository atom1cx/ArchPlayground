package com.space.arch.playground.domain.components.create.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.space.arch.playground.domain.components.create.store.CreateStore.Intent
import com.space.arch.playground.domain.components.create.store.CreateStore.Label
import com.space.arch.playground.domain.components.create.store.CreateStore.State
import com.space.arch.playground.domain.components.create.store.CreateStore.Message
import com.space.arch.playground.domain.model.ItemType
import com.space.arch.playground.domain.model.ListItem
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateExecutor(
    private val repository: ItemsRepository
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {
    override fun executeIntent(intent: Intent) {
        when(intent) {
            is Intent.ChangeSubtitle -> {
                dispatch(
                    Message.SubtitleChanged(
                        intent.subtitle
                    )
                )
                dispatch(
                    Message.ChangeCanSave(
                        canSave()
                    )
                )
            }
            is Intent.ChangeTitle -> {
                dispatch(
                    Message.TitleChanged(
                        intent.title
                    )
                )
                dispatch(
                    Message.ChangeCanSave(
                        canSave()
                    )
                )
            }
            is Intent.ChangeType -> {
                dispatch(
                    Message.TypeChanged(
                        intent.checked
                    )
                )
            }
            Intent.Save -> {
                scope.launch {
                    val state = state()
                    try {
                        dispatch(
                            Message.Loading(true)
                        )
                        withContext(Dispatchers.Default) {
                            repository.addItem(
                                ListItem(
                                    id = 0,
                                    title = state.title,
                                    subtitle = state.subtitle,
                                    pictureUrl = state.pictureUrl,
                                    type = if(!state.checkedType) {
                                        ItemType.FIRST
                                    } else {
                                        ItemType.SECOND
                                    }
                                )
                            )
                            publish(Label.Created)
                        }
                    } catch (e: Throwable) {
                        publish(Label.ShowError(e.message ?: "Unknown error"))
                    } finally {
                        dispatch(Message.Loading(false))
                    }

                }
            }
        }
    }

    private fun canSave() : Boolean {
        return with(state()) {
            title.isNotEmpty() && subtitle.isNotEmpty()
        }
    }
}
