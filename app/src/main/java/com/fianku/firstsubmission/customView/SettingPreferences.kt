package com.fianku.firstsubmission.customView

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val NAME = stringPreferencesKey("name")
    private val USER_ID = stringPreferencesKey("userid")
    private val TOKEN = stringPreferencesKey("token")
    val getUserTokenBro: Flow<String?> = dataStore.data.map {
            it[TOKEN]
        }
    fun getUserName(): Flow<String?> {
        return dataStore.data.map { it[NAME] }
    }

    suspend fun setUserName(data: String) {
        dataStore.edit {
          it[NAME] = data
        }
    }
    fun getUserId(): Flow<String?> {
        return dataStore.data.map {
            it[USER_ID]
        }
    }

    suspend fun setUserId(data: String) {
        dataStore.edit {
            it[USER_ID] = data
        }
    }
    fun getUserToken(): Flow<String?> {
        return dataStore.data.map {
            it[TOKEN]
        }
    }

    suspend fun setUserToken(data: String) {
        dataStore.edit {
            it[TOKEN] = data
        }
    }
    suspend fun deleteAllData(){
        dataStore.edit {
            it.clear()
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}