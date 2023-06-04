package com.fianku.firstsubmission.ui.detailUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.fianku.firstsubmission.network.ListStoryItem
import com.fianku.firstsubmission.databinding.ActivityDetailBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
    }
    private fun setupData() {
        val data = intent.getParcelableExtra<ListStoryItem>("data") as ListStoryItem
        Glide.with(applicationContext)
            .load(data.photoUrl)
            .circleCrop()
            .into(binding.profileImageView)
        binding.nameTextView.text = data.name
        binding.dateTextView.text = data.createdAt?.withDateFormat() ?: "-"
        binding.descTextView.text = data.description
    }
    private fun String.withDateFormat(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val date = format.parse(this) as Date
        return DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
    }
}