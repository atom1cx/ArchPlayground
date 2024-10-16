package com.space.arch.playground.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.space.arch.playground.domain.components.list.ListComponent
import com.space.arch.playground.domain.components.list.PreviewListComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    component: ListComponent,
    modifier: Modifier = Modifier,
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

@Composable
@Preview
private fun ListContentPreview() {
    ListContent(component = PreviewListComponent())
}