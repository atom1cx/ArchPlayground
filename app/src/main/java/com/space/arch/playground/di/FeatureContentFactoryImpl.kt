package com.space.arch.playground.di

import com.space.arch.playground.arch.core.FeatureComponent
import com.space.arch.playground.arch.core.FeatureContent
import com.space.arch.playground.arch.core.FeatureContentFactory
import com.space.arch.playground.features.create.api.CreateComponent
import com.space.arch.playground.features.create.impl.CreateFeatureContent
import com.space.arch.playground.features.details.api.DetailsComponent
import com.space.arch.playground.features.details.impl.DetailsFeatureContent
import com.space.arch.playground.features.list.api.ListComponent
import com.space.arch.playground.features.list.impl.ListFeatureContent

class FeatureContentFactoryImpl : FeatureContentFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : FeatureComponent> create(featureComponent: FeatureComponent): FeatureContent<T> {
        return when (featureComponent) {
            is ListComponent -> {
                ListFeatureContent() as FeatureContent<T>
            }

            is CreateComponent -> {
                CreateFeatureContent() as FeatureContent<T>
            }

            is DetailsComponent -> {
                DetailsFeatureContent() as FeatureContent<T>
            }

            else -> throw IllegalArgumentException("Unknown feature component: $featureComponent")
        }
    }
}