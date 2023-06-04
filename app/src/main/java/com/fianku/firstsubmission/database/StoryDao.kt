package com.fianku.firstsubmission.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fianku.firstsubmission.network.StoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<StoryItem>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, StoryItem>
    @Query("SELECT * FROM story")
    fun getStory(): List<StoryItem>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}
