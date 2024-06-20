package com.capstone.skinifier.ui.article.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.skinifier.GlideApp
import com.capstone.skinifier.R
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_ARTICLE = "detailArticle"
    }

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_ARTICLE, ArticleDataModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(DETAIL_ARTICLE) as? ArticleDataModel
        }

        binding.imageButton.setOnClickListener {
            onBackPressed()
            finish()
        }
        data?.let {
            with(binding) {
                title.text = data.judul
                description.text = data.deskripsi
                GlideApp.with(this.root).load(it.foto).into(binding.photo)
            }
        }

    }
}