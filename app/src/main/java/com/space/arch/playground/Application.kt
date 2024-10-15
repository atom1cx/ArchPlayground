package com.space.arch.playground

import android.app.Application
import com.space.arch.playground.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            initKoin(
                appDeclaration = {
                    androidContext(this@Application)
                }
            )
        }
    }
}