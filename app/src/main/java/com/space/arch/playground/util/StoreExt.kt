package com.space.arch.playground.util

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.rx.observer
import com.arkivanov.mvikotlin.core.store.Store

fun <T : Any> Store<*, T, *>.stateAsValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state
        override fun subscribe(observer: (T) -> Unit): Cancellation {
            val disposable = states(observer(onNext = observer))
            return Cancellation {
                disposable.dispose()
            }
        }
    }

fun <T : Any> Store<*, *, T>.labelsAsValue(skipValue: T): Value<T> =
    object : Value<T>() {
        override val value: T get() = skipValue
        override fun subscribe(observer: (T) -> Unit): Cancellation {
            val disposable = labels(observer(onNext = {
                observer(it)
            }))
            return Cancellation {
                disposable.dispose()
            }
        }
    }