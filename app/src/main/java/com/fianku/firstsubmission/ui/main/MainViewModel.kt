package com.fianku.firstsubmission.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.fianku.firstsubmission.customView.Event
import com.fianku.firstsubmission.customView.SettingPreferences
import com.fianku.firstsubmission.data.StoryRepository
import com.fianku.firstsubmission.network.StoryItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences, private val storyRepository: StoryRepository): ViewModel() {

    var story: LiveData<PagingData<StoryItem>>? = null
    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        viewModelScope.launch {
            pref.getUserTokenBro.collect {
                story = storyRepository.getQuote(it).cachedIn(viewModelScope)
            }
        }
    }
    fun getUserNames(): LiveData<String?> {
        return pref.getUserName().asLiveData()
    }
}