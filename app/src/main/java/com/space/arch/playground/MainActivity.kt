package com.space.arch.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.di.koin
import com.space.arch.playground.domain.RootComponent
import com.space.arch.playground.ui.compose.App

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponentFactory: RootComponent.Factory by koin.inject()

        val featureContentFactory: FeatureContentFactory by koin.inject()

        val rootComponent = rootComponentFactory.invoke(defaultComponentContext())

        setContent {
            App(
                rootComponent = rootComponent,
                featureContentFactory = featureContentFactory
            )
        }
    }
}