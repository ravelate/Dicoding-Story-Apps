package com.fianku.firstsubmission.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fianku.firstsubmission.databinding.ItemStoryBinding
import com.fianku.firstsubmission.network.ListStoryItem
import com.fianku.firstsubmission.network.StoryItem
import com.fianku.firstsubmission.ui.detailUser.DetailStoryActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class StoryAdapter :
    PagingDataAdapter<StoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }
    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoryItem) {
            binding.tvName.text = data.name
            binding.tvDate.text = data.createdAt?.withDateFormat()
            Glide.with(binding.root.context)
                .load("${data.photoUrl}")
                .into(binding.imgItemPhoto)
            binding.tvDesc.text = data.description
           itemView.setOnClickListener {
                val parce = ListStoryItem()
                    parce.photoUrl = data.photoUrl
                    parce.createdAt = data.createdAt
                    parce.name = data.name
                    parce.description = data.description
                    parce.lon = data.lon
                    parce.id = data.id
                    parce.lat = data.lat
               val i = Intent(itemView.context, DetailStoryActivity::class.java)
               i.putExtra("data",parce)
               val optionsCompat: ActivityOptionsCompat =
                   ActivityOptionsCompat.makeSceneTransitionAnimation(
                       itemView.context as Activity,
                       androidx.core.util.Pair(binding.imgItemPhoto, "profile"),
                       androidx.core.util.Pair(binding.tvName, "name"),
                       androidx.core.util.Pair(binding.tvDate, "date"),
                       androidx.core.util.Pair(binding.tvDesc, "description"),
                   )
               itemView.context.startActivity(i, optionsCompat.toBundle())

            }
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
        private fun String.withDateFormat(): String {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val date = format.parse(this) as Date
            return DateFormat.getDateInstance(DateFormat.FULL).format(date)
        }
    }

}

