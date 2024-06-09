package com.capstone.skinifier

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.skinifier.databinding.ActivityArticleBinding


class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}