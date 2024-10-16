package com.space.arch.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.space.arch.playground.di.koin
import com.space.arch.playground.domain.components.root.RootComponent
import com.space.arch.playground.ui.compose.App
import com.space.arch.playground.ui.theme.ArchPlaygroundTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponentFactory: RootComponent.Factory by koin.inject()

        val rootComponent = rootComponentFactory.invoke(defaultComponentContext())

        setContent {
            App(
                rootComponent = rootComponent
            )
        }
    }
}