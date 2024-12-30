package com.space.arch.playground.features.details.impl.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.space.arch.playground.repository.api.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class DetailsExecutor(
    private val id: Long,
    private val repository: ItemsRepository
) : CoroutineExecutor<DetailsStore.Intent, DetailsStore.Action, DetailsStore.State, DetailsStore.Message, DetailsStore.Label>() {
    override fun executeAction(action: DetailsStore.Action) {
        when (action) {
            is DetailsStore.Action.DataLoaded -> {
                dispatch(
                    DetailsStore.Message.UpdateData(
                        action.data
                    )
                )
            }

            is DetailsStore.Action.PostLoadFailed -> {
                dispatch(
                    DetailsStore.Message.ShowError(
                        errorFromThrowable(action.throwable)
                    )
                )
            }
        }
    }

    override fun executeIntent(intent: DetailsStore.Intent) {
        when (intent) {
            DetailsStore.Intent.TryAgain -> {
                dispatch(
                    DetailsStore.Message.Loading
                )
                scope.launch {
                    repository.getItem(id)
                        .flowOn(Dispatchers.Default)
                        .catch { dispatch(DetailsStore.Message.ShowError(errorFromThrowable(it))) }
                        .collect { dispatch(DetailsStore.Message.UpdateData(it)) }
                }
            }
        }
    }

    private fun errorFromThrowable(throwable: Throwable): String {
        return throwable.message ?: "Loading Failed"
    }
}
