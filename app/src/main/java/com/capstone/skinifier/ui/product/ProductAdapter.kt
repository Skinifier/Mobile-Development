package com.capstone.skinifier.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.skinifier.R
import com.capstone.skinifier.data.model.ProductModelItem

class ProductAdapter(private val navigateToDetail: (ProductModelItem) -> Unit = {}) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val listProducts = mutableListOf<ProductModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = listProducts[position]
        holder.bind(product, navigateToDetail)
    }

    override fun getItemCount(): Int = listProducts.size


    fun submitData(newData: List<ProductModelItem>) {
        listProducts.clear()
        listProducts.addAll(newData)
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View,) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.nama_brand)
        private val productType: TextView = itemView.findViewById(R.id.jenis_produk)
        private val productImage: ImageView = itemView.findViewById(R.id.photo)
        fun bind(product: ProductModelItem, navigateToDetail: (ProductModelItem) -> Unit) {
            itemView.rootView.setOnClickListener {
                navigateToDetail.invoke(product)
            }
            productName.text = product.namaBarang
            productType.text = product.jenisProduk
            Glide.with(itemView.context)
                .load(product.foto)
                .placeholder(R.drawable.placeholder_product)
                .into(productImage)
        }
    }
}
