package com.space.arch.playground.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.space.arch.playground.domain.components.root.RootComponent
import com.space.arch.playground.ui.theme.ArchPlaygroundTheme

@Composable
fun App(rootComponent: RootComponent) {
    ArchPlaygroundTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            RootContent(
                component = rootComponent,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}