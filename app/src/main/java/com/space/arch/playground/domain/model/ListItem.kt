package com.space.arch.playground.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ListItem(
    val id: Long,
    val title: String,
    val subtitle: String,
    val pictureUrl: String,
    val type: ItemType
) {
    enum class Type {
        FIRST,
        SECOND
    }
}