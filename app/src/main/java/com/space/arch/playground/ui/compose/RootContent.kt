package com.space.arch.playground.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.space.arch.playground.domain.components.root.PreviewRootComponent
import com.space.arch.playground.domain.components.root.RootComponent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.DetailsFeature -> {
                DetailsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RootComponent.Child.List -> {
                ListContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RootComponent.Child.CreateFeature -> {
                CreateContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
@Preview
private fun RootContentPreview() {
    RootContent(component = PreviewRootComponent())
}