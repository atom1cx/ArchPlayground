package com.space.arch.playground

import android.app.Application
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer

class App : Application() {
    private val timeTravelServer = TimeTravelServer()

    override fun onCreate() {
        super.onCreate()
        timeTravelServer.start()
    }
}