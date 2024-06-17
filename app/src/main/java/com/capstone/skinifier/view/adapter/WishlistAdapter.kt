package com.capstone.skinifier.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.skinifier.data.response.DetailBarangResponse
import com.capstone.skinifier.databinding.ProductRectangleRowBinding

class WishlistAdapter : ListAdapter<DetailBarangResponse, WishlistAdapter.WishlistViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ProductRectangleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(binding)
    }


    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WishlistViewHolder(private val binding: ProductRectangleRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailBarangResponse) {
            binding.title.text = item.namaBrand
            binding.type.text = item.jenisProduk
            binding.skinType.text = item.skinType
            Glide.with(binding.photo.context).load(item.foto).into(binding.photo)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DetailBarangResponse>() {
        override fun areItemsTheSame(oldItem: DetailBarangResponse, newItem: DetailBarangResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailBarangResponse, newItem: DetailBarangResponse): Boolean {
            return oldItem == newItem
        }
    }
}
