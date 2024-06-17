package com.capstone.skinifier.view.soldProduct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.skinifier.R
import com.capstone.skinifier.databinding.ActivitySoldProductBinding
import com.capstone.skinifier.databinding.ActivityWishlistBinding
import com.capstone.skinifier.view.adapter.SoldProductAdapter
import com.capstone.skinifier.view.adapter.WishlistAdapter
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory
import com.capstone.skinifier.view.wishlist.WishlistViewModel

class SoldProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoldProductBinding
    private val soldProductViewModel by viewModels<SoldProductViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoldProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SoldProductAdapter()
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.adapter = adapter

        soldProductViewModel.soldProductItems.observe(this, Observer { items ->
            items?.let {
                adapter.submitList(it)
            }
        })

        soldProductViewModel.fetchSoldProducts()
    }
}