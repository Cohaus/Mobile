package com.solution.gdsc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}