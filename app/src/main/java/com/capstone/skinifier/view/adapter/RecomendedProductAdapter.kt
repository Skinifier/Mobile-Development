package com.capstone.skinifier.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.skinifier.R
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.response.GetAllBarangResponseItem

class RecomendedProductAdapter(
    private val productList: List<GetAllBarangResponseItem>,
    private val onItemClick: (ProductModelItem) -> Unit
) : RecyclerView.Adapter<RecomendedProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImageView: ImageView = view.findViewById(R.id.photo)
        val brandNameTextView: TextView = view.findViewById(R.id.nama_brand)
        val productTypeTextView: TextView = view.findViewById(R.id.jenis_produk)

        fun bind(product: GetAllBarangResponseItem) {
            val imageUrl = product.foto
            if (imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(R.drawable.placeholder_product)
                    .into(productImageView)
            } else {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.placeholder_product)
                    .into(productImageView)
            }

            brandNameTextView.text = product.namaBrand
            productTypeTextView.text = product.jenisProduk

            itemView.setOnClickListener {
                val productModel = ProductModelItem(
                    namaBrand = product.namaBrand ?: "",
                    noHp = product.noHp ?: "",
                    bahan = product.bahan ?: "",
                    createdAt = product.createdAt ?: "",
                    idUser = product.idUser ?: "",
                    skinType = product.skinType ?: "",
                    harga = product.harga ?: "",
                    updatedAt = product.updatedAt ?: "",
                    jenisProduk = product.jenisProduk ?: "",
                    namaBarang = product.namaBarang ?: "",
                    id = product.id ?: "",
                    deskripsi = product.deskripsi ?: "",
                    domisili = product.domisili ?: "",
                    foto = product.foto ?: ""
                )
                onItemClick(productModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size
}