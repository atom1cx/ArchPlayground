package com.space.arch.playground.features.create.impl

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.space.arch.playground.arch.core.FeatureContent
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.features.create.api.CreateComponent
import kotlinx.coroutines.launch

class CreateFeatureContent : FeatureContent<CreateComponent> {
    @Suppress("ComposableNaming")
    @Composable
    override operator fun invoke(
        component: CreateComponent,
        featureContentFactory: FeatureContentFactory,
        modifier: Modifier
    ) {
        val state by component.model.subscribeAsState()

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect("ShowError") {
            component.event.subscribe {
                when (it) {
                    is CreateComponent.Event.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar(it.text)
                        }
                    }

                    else -> {}
                }
            }
        }

        Scaffold(
            modifier = modifier,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            topBar = {
                TopAppBar(
                    title = { Text("Create") },
                    navigationIcon = {
                        IconButton(onClick = component::onBackPressed) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = component::onTitleChanged,
                    label = { Text("Title") },
                )
                OutlinedTextField(
                    value = state.subtitle,
                    onValueChange = component::onSubtitleChanged,
                    label = { Text("Description") },
                )
                Row {
                    Text("First type")
                    Switch(
                        checked = state.typeChecked,
                        onCheckedChange = component::onTypeSwitched
                    )
                    Text("Second type")
                }

                AnimatedContent(state.loading) { isLoading ->
                    if (isLoading) CircularProgressIndicator()
                    else {
                        Button(
                            onClick = component::onSaveClicked,
                            enabled = state.canSave,
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}