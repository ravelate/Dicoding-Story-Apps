package com.fianku.firstsubmission.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fianku.firstsubmission.adapter.LoadingStateAdapter
import com.fianku.firstsubmission.ui.addPhoto.AddPhotoActivity
import com.fianku.firstsubmission.ui.detailUser.DetailUserActivity
import com.fianku.firstsubmission.adapter.StoryAdapter
import com.fianku.firstsubmission.customView.SettingPreferences
import com.fianku.firstsubmission.customView.ViewModelFactory
import com.fianku.firstsubmission.databinding.ActivityMainBinding
import com.fianku.firstsubmission.ui.map.MapsActivity

import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            MainViewModel::class.java
        )
        mainViewModel.getUserNames().observe(this) {
            binding.tvUsername.text = it
        }
        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        binding.ibUserDetail.setOnClickListener{
            startActivity(Intent(this, DetailUserActivity::class.java))
        }
        binding.ibLocation.setOnClickListener{
            startActivity(Intent(this, MapsActivity::class.java))
        }
        binding.ibAddStory.setOnClickListener{
            startActivity(Intent(this, AddPhotoActivity::class.java))
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        setReviewData()
    }
    private fun setReviewData() {
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref,this)).get(
            MainViewModel::class.java
        )
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        val adapter = StoryAdapter()
        binding.rvUsers.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.story?.observe(this) {
            adapter.submitData(lifecycle,it)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }
}