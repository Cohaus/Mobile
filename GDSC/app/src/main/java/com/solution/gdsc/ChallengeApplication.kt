package com.solution.gdsc

import android.app.Application
import com.solution.gdsc.util.TokenManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ChallengeApplication : Application() {

    @Inject
    lateinit var tokenManager: TokenManager

    companion object {
        private lateinit var application: ChallengeApplication

        fun getInstance(): ChallengeApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}