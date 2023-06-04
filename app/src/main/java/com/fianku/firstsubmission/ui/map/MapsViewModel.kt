package com.fianku.firstsubmission.ui.map


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fianku.firstsubmission.data.StoryRepository
import com.fianku.firstsubmission.network.StoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    var story: List<StoryItem>? = null
    init {
        viewModelScope.launch {
                story = storyRepository.getStoriesForMap()
        }
    }
}