package com.space.arch.playground.ui.compose

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.space.arch.playground.domain.components.create.CreateComponent
import com.space.arch.playground.domain.components.create.PreviewCreateComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateContent(
    component: CreateComponent,
    modifier: Modifier = Modifier,
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
            Log.e("RECOMPOSED!", "CreateContentView")
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
            Switch(
                checked = state.typeChecked,
                onCheckedChange = component::onTypeSwitched
            )

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

@Composable
@Preview
private fun CreateContentPreview() {
    CreateContent(
        component = PreviewCreateComponent()
    )
}
