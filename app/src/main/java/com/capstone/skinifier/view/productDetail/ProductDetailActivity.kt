package com.capstone.skinifier.view.productDetail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.capstone.skinifier.R
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.databinding.ActivityProductDetailBinding
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_DETAIL = "productDetail"
    }
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel by viewModels<ProductDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var isWishlisted = false
    private var wishlistItemId: String? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        onBackClickListener()
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PRODUCT_DETAIL, ProductModelItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(PRODUCT_DETAIL) as? ProductModelItem
        }

        binding.imageButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        product?.let {model ->
            with(binding) {
                Glide.with(this@ProductDetailActivity)
                    .load(model.foto)
                    .into(imageProduct)
                itemPrice.text = "Rp" + model.harga
                itemBrand.text = model.namaBrand
                itemType.text = model.skinType
                itemDescription.text = model.deskripsi
                itemDomicile.text = model.domisili
                itemIngredient.text = model.bahan
                itemNowhatsapp.setOnClickListener {
                    val phoneNumber = model.noHp ?: return@setOnClickListener  // Get the phone number, or return if null
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
                    }
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@ProductDetailActivity, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
                    }
                }

                checkWishlistStatus(model.id)

                val favoriteClickListener = {
                    if (isWishlisted) {
                        removeProductFromWishlist()
                    } else {
                        addProductToWishlist(model.id)
                    }
                }

                frameFavorite.setOnClickListener { favoriteClickListener() }
                btnFavorite.setOnClickListener { favoriteClickListener() }
            }
        }
    }

    private fun checkWishlistStatus(productId: String) {
        viewModel.getWishlistItems { wishlistItems ->
            wishlistItems.forEach { item ->
                if (item.idBarang == productId) {
                    isWishlisted = true
                    wishlistItemId = item.idWishlist
                    binding.btnFavorite.setImageResource(R.drawable.ic_favfill)
                }
            }
        }
    }

    private fun addProductToWishlist(productId: String) {
        viewModel.addWishlistItem(productId, 1) { response ->
            if (response.message == "Wishlist added successfully") {
                isWishlisted = true
                binding.btnFavorite.setImageResource(R.drawable.ic_favfill)
                Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to add to wishlist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeProductFromWishlist() {
        wishlistItemId?.let { wishlistId ->
            viewModel.deleteWishlistItem(wishlistId) { response ->
                if (response.message == "Wishlist item deleted successfully") {
                    isWishlisted = false
                    binding.btnFavorite.setImageResource(R.drawable.ic_favborder)
                    Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to remove from wishlist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun onBackClickListener() {
        onBackPressedDispatcher.addCallback {
            finish()
        }

    }
}