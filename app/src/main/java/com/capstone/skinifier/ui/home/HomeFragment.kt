package com.capstone.skinifier.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.skinifier.view.productDetail.ProductDetailActivity
import com.capstone.skinifier.view.productDetail.ProductDetailActivity.Companion.PRODUCT_DETAIL
import com.capstone.skinifier.data.model.ArticleDataModel
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.databinding.FragmentHomeBinding
import com.capstone.skinifier.ui.adapter.ArticleAdapter
import com.capstone.skinifier.ui.article.ArticleActivity
import com.capstone.skinifier.ui.article.detail.ArticleDetailActivity
import com.capstone.skinifier.ui.product.ProductAdapter
import com.capstone.skinifier.view.viewModelFactory.ProductViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels { ProductViewModelFactory.getInstance(requireContext()) }

    override fun onResume() {
        super.onResume()
        viewModel.fetchProfile()
        viewModel.fetchArticles()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.fetchProfile()
        viewModel.fetchArticles()
        val root: View = binding.root

        val rvProductsAdapter = ProductAdapter {
            navigateToDetail(it)
        }

        val rvArticleAdapter = ArticleAdapter {
            navigateToDetailArticle(it)
        }

        binding.recommendationRecyclerView.apply {
            adapter = rvProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.articleRecyclerView.apply {
            adapter = rvArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.articleButton.setOnClickListener {
            Intent(requireActivity(), ArticleActivity::class.java).run {
                startActivity(this)
            }
        }

        viewModel.products.observe(viewLifecycleOwner) {
            rvProductsAdapter.submitData(it)
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            rvArticleAdapter.submitData(it)
        }

        viewModel.profile.observe(viewLifecycleOwner) {
            with(binding) {
                welcomeText.text = "Hello, ${it.fullname}"
                skinCondition.text = "Your Skin condition is, ${it.skinType}"
                Glide.with(root.context).load(it.photo).into(userImage)
            }
            viewModel.fetchRecProducts(it.skinType)
        }

        return root
    }

    private fun navigateToDetailArticle(id: ArticleDataModel) {
        Intent(requireActivity(), ArticleDetailActivity::class.java).apply {
            putExtra(ArticleDetailActivity.DETAIL_ARTICLE, id)
            startActivity(this)
        }
    }
    private fun navigateToDetail(product: ProductModelItem) {
        Intent(requireActivity(), ProductDetailActivity::class.java).apply {
            putExtra(PRODUCT_DETAIL, product)
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}