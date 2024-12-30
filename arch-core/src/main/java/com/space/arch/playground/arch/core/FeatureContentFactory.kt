package com.space.arch.playground.arch.core

interface FeatureContentFactory {
    fun <T : FeatureComponent> create(featureComponent: FeatureComponent): FeatureContent<T>
}