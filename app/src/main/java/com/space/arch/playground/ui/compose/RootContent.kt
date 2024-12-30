package com.space.arch.playground.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.di.FeatureContentFactoryImpl
import com.space.arch.playground.domain.PreviewRootComponent
import com.space.arch.playground.domain.RootComponent
import com.space.arch.playground.features.create.api.CreateComponent
import com.space.arch.playground.features.details.api.DetailsComponent
import com.space.arch.playground.features.list.api.ListComponent

@Composable
fun RootContent(
    component: RootComponent,
    featureContentFactory: FeatureContentFactory,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Details -> {
                featureContentFactory.create<DetailsComponent>(child.component)(
                    component = child.component,
                    featureContentFactory = featureContentFactory,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is RootComponent.Child.List -> {
                featureContentFactory.create<ListComponent>(child.component)(
                    component = child.component,
                    featureContentFactory = featureContentFactory,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RootComponent.Child.Create -> {
                featureContentFactory.create<CreateComponent>(child.component)(
                    component = child.component,
                    featureContentFactory = featureContentFactory,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
@Preview
private fun RootContentPreview() {
    RootContent(
        component = PreviewRootComponent(),
        featureContentFactory = FeatureContentFactoryImpl()
    )
}