package com.fianku.firstsubmission.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.fianku.firstsubmission.database.StoryDatabase
import com.fianku.firstsubmission.network.*

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getQuote(s: String?): LiveData<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService,s),
            pagingSourceFactory = {
//                StoryPagingSource(apiService,s)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
     fun getStoriesForMap(): List<StoryItem> {
        return storyDatabase.storyDao().getStory()
    }
}