package com.capstone.skinifier.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.databinding.ArticleRowBinding

class ArticleAdapter(private val onClick: (ArticleDataModel) -> Unit = {}): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val articles = mutableListOf<ArticleDataModel>()
    class ArticleViewHolder(val binding: ArticleRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articles[position]
        with(holder.binding) {
            root.setOnClickListener {
                onClick.invoke(item)
            }
            title.text = item.judul
            description.text = item.deskripsi
            Glide.with(holder.itemView.context)
                .load(item.foto)
                .into(this.photo)
        }
    }

    fun submitData(newData: List<ArticleDataModel>) {
        articles.clear()
        articles.addAll(newData)
        notifyDataSetChanged()
    }
}