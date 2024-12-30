package com.space.arch.playground.arch.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface FeatureContent<T : FeatureComponent> {

    @Composable
    operator fun invoke(
        component: T,
        featureContentFactory: FeatureContentFactory,
        modifier: Modifier
    )
}