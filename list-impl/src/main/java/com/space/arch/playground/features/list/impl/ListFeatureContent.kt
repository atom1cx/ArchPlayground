package com.space.arch.playground.features.list.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.space.arch.playground.arch.core.FeatureContent
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.features.list.api.ListComponent

class ListFeatureContent : FeatureContent<ListComponent> {
    @Suppress("ComposableNaming")
    @Composable
    override operator fun invoke(
        component: ListComponent,
        featureContentFactory: FeatureContentFactory,
        modifier: Modifier
    ) {
        val state by component.model.subscribeAsState()

        Scaffold(
            modifier = modifier,
            topBar = { TopAppBar(title = { Text("List") }) },
            floatingActionButton = {
                FloatingActionButton(component::createNewItem) {
                    Icon(Icons.Outlined.Add, contentDescription = "")
                }
            },
        ) { paddingValues ->
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(items = state) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { component.itemClicked(item.id) }
                            .padding(16.dp)
                    ) {
                        Text(item.title)
                    }
                }
            }
        }
    }
}