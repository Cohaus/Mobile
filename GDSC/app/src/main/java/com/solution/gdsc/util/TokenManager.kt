package com.solution.gdsc.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.solution.gdsc.util.extensions.datastore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.datastore

    suspend fun saveAccessToken(accessToken: String) {
        if (accessToken.isNotEmpty()) {
            dataStore.edit { preferences ->
                preferences[KEY_ACCESS_TOKEN] = accessToken
            }
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        if (refreshToken.isNotEmpty()) {
            dataStore.edit { preferences ->
                preferences[KEY_REFRESH_TOKEN] = refreshToken
            }
        }
    }

    suspend fun checkUserToken(): Boolean {
        var isLogin = false
        accessTokenFlow.collect {
            if (!it.isNullOrEmpty())
                isLogin = true
        }
        return isLogin
    }

    private val accessTokenFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_ACCESS_TOKEN]
        }

    val refreshTokenFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_REFRESH_TOKEN]
        }

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val KEY_LOGIN_CHECK = stringPreferencesKey("login_check")
    }
}