package com.capstone.skinifier.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.skinifier.data.response.SoldProductResponseItem
import com.capstone.skinifier.databinding.ProductRectangleRowBinding

class SoldProductAdapter : ListAdapter<SoldProductResponseItem, SoldProductAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<SoldProductResponseItem>() {
        override fun areItemsTheSame(oldItem: SoldProductResponseItem, newItem: SoldProductResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SoldProductResponseItem, newItem: SoldProductResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductRectangleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: ProductRectangleRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SoldProductResponseItem) {
            binding.title.text = item.namaBrand
            binding.type.text = item.jenisProduk
            binding.skinType.text = item.skinType
            // Load image into ShapeableImageView, e.g., using Glide or Picasso
            Glide.with(binding.photo.context).load(item.foto1).into(binding.photo)
        }
    }
}