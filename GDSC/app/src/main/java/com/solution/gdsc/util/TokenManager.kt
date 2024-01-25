package com.solution.gdsc.util

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.solution.gdsc.util.extensions.datastore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.datastore

    val accessTokenFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_ACCESS_TOKEN]
        }

    val refreshTokenFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[KEY_REFRESH_TOKEN]
        }

    suspend fun getAccessToken(): String {
        return dataStore.data.first().toString()
    }

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

    fun checkUserToken(): Boolean {
        var isLogin = false
        Log.e("LoginCheck", "LoginCheck")
        dataStore.data.map {
            if (!it[KEY_ACCESS_TOKEN].isNullOrEmpty()) {
                isLogin = true
            }
        }
        return isLogin
    }

    companion object {
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}