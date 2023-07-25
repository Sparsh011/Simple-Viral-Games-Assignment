package com.example.simpleviralgamesassignment.views.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleviralgamesassignment.R
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity

class CachedDogsAdapter (
    private val context: Context
): RecyclerView.Adapter<CachedDogsAdapter.CachedDogsViewHolder>() {
    private var TAG = "CachedDogsRv"

    private val differCallback = object : DiffUtil.ItemCallback<CachedDogsEntity?>(){
        override fun areItemsTheSame(oldItem: CachedDogsEntity, newItem: CachedDogsEntity): Boolean {
            return oldItem.id == newItem.id && oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: CachedDogsEntity, newItem: CachedDogsEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachedDogsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cached_dog, parent, false)
        return CachedDogsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CachedDogsViewHolder, position: Int) {
        val currentDog = differ.currentList[position]
        Log.d(TAG, "onBindViewHolder: ${currentDog?.imageUrl}")
        currentDog?.let {
            setImage(currentDog.imageUrl, context, holder.cachedDogImage)
        }
    }

    class CachedDogsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cachedDogImage: AppCompatImageView = itemView.findViewById(R.id.iv_cached_dog)
    }

    private fun setImage(url: String, context: Context, cachedDogImage: AppCompatImageView) {
        Glide.with(context)
            .load(url)
            .into(cachedDogImage)
    }
}