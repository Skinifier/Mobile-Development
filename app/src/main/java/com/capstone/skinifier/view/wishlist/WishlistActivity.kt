package com.capstone.skinifier.view.wishlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.skinifier.databinding.ActivityWishlistBinding
import com.capstone.skinifier.view.adapter.WishlistAdapter
import com.capstone.skinifier.view.productDetail.ProductDetailActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    private val wishlistViewModel by viewModels<WishlistViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        wishlistAdapter = WishlistAdapter { productModel ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(ProductDetailActivity.PRODUCT_DETAIL, productModel)
            startActivity(intent)
        }

        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(this@WishlistActivity)
            adapter = wishlistAdapter
        }

        wishlistViewModel.wishlistItems.observe(this) { items ->
            Log.d("WishlistActivity", "Wishlist items updated: $items")
            wishlistAdapter.submitList(items)
        }

        binding.backButton.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        wishlistViewModel.fetchWishlist()
    }

    override fun onResume() {
        super.onResume()
        wishlistViewModel.fetchWishlist()
    }
}