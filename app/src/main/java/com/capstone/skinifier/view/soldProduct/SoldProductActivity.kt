package com.capstone.skinifier.view.soldProduct

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.skinifier.databinding.ActivitySoldProductBinding
import com.capstone.skinifier.view.adapter.SoldProductAdapter
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class SoldProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoldProductBinding
    private val soldProductViewModel by viewModels<SoldProductViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoldProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val adapter = SoldProductAdapter()
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.adapter = adapter

        soldProductViewModel.soldProductItems.observe(this, Observer { items ->
            items?.let {
                adapter.submitList(it)
            }
        })

        binding.backButton.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        soldProductViewModel.fetchSoldProducts()
    }
}