package com.space.arch.playground.domain.components.details.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.space.arch.playground.domain.components.details.store.DetailsStore.Action
import com.space.arch.playground.domain.components.details.store.DetailsStore.Intent
import com.space.arch.playground.domain.components.details.store.DetailsStore.Label
import com.space.arch.playground.domain.components.details.store.DetailsStore.Message
import com.space.arch.playground.domain.components.details.store.DetailsStore.State
import com.space.arch.playground.domain.repositories.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailsExecutor(
    private val id: Long,
    private val repository: ItemsRepository
) : CoroutineExecutor<Intent, Action, State, Message, Label>() {
    override fun executeAction(action: Action) {
        when (action) {
            is Action.DataLoaded -> {
                dispatch(
                    Message.UpdateData(
                        action.data
                    )
                )
            }

            is Action.PostLoadFailed -> {
                dispatch(
                    Message.ShowError(
                        errorFromThrowable(action.throwable)
                    )
                )
            }
        }
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            Intent.TryAgain -> {
                dispatch(
                    Message.Loading
                )
                scope.launch {
                    repository.getItem(id)
                        .flowOn(Dispatchers.Default)
                        .catch { dispatch(Message.ShowError(errorFromThrowable(it))) }
                        .collect { dispatch(Message.UpdateData(it)) }
                }
            }
        }
    }

    private fun errorFromThrowable(throwable: Throwable): String {
        return throwable.message ?: "Loading Failed"
    }
}
