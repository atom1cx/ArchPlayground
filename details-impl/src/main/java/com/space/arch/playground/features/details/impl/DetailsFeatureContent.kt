package com.space.arch.playground.features.details.impl

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.space.arch.playground.arch.core.FeatureContent
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.features.details.api.DetailsComponent

class DetailsFeatureContent : FeatureContent<DetailsComponent> {
    @Suppress("ComposableNaming")
    @Composable
    override operator fun invoke(
        component: DetailsComponent,
        featureContentFactory: FeatureContentFactory,
        modifier: Modifier
    ) {

        val state by component.model.subscribeAsState()

        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Details")
                    },
                    navigationIcon = {
                        IconButton(onClick = component::onBackPressed) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                AnimatedContent(state) { contentState ->
                    when (contentState) {
                        DetailsComponent.Model.Loading -> CircularProgressIndicator()
                        is DetailsComponent.Model.Error -> Button(
                            onClick = component::onTryAgain
                        ) { Text(contentState.text) }

                        is DetailsComponent.Model.Data -> Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(contentState.data.title)
                            Text(contentState.data.subtitle)
                        }
                    }
                }
            }
        }
    }
}