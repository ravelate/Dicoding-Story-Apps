package com.fianku.firstsubmission.di

import android.content.Context
import com.fianku.firstsubmission.data.StoryRepository
import com.fianku.firstsubmission.database.StoryDatabase
import com.fianku.firstsubmission.network.ApiConfig

object Injection {
      fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}