package com.capstone.skinifier.ui.article

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.databinding.ActivityArticleBinding
import com.capstone.skinifier.ui.adapter.ArticleAdapter
import com.capstone.skinifier.ui.article.detail.ArticleDetailActivity
import com.capstone.skinifier.ui.article.detail.ArticleDetailActivity.Companion.DETAIL_ARTICLE


class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private val viewModel: ArticleViewModel by viewModels { ArticleViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchArticles()

        val rvAdapter = ArticleAdapter {
            navigateToDetailArticle(it)
        }

        binding.rvArticle.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@ArticleActivity)
        }

        viewModel.articles.observe(this) {
            rvAdapter.submitData(it)
        }

        binding.imageButton.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    fun navigateToDetailArticle(id: ArticleDataModel) {
        Intent(this, ArticleDetailActivity::class.java).apply {
            putExtra(DETAIL_ARTICLE, id)
            startActivity(this)
        }
    }
}