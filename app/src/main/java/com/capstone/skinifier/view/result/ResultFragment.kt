package com.capstone.skinifier.view.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.skinifier.R
import com.capstone.skinifier.data.response.GetAllBarangResponseItem
import com.capstone.skinifier.view.adapter.RecomendedProductAdapter
import com.capstone.skinifier.view.productDetail.ProductDetailActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResultFragment : BottomSheetDialogFragment() {

    private val resultViewModel: ResultViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var skinTextView: TextView
    private lateinit var bestProductTextView: TextView
    private lateinit var skinConditionTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecomendedProductAdapter

    private var skinCondition: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        skinTextView = view.findViewById(R.id.your_skin)
        bestProductTextView = view.findViewById(R.id.best_product)
        skinConditionTextView = view.findViewById(R.id.skin_condition)
        recyclerView = view.findViewById(R.id.horizontal_rv_product)

        skinCondition = arguments?.getString("SKIN_CONDITION")
        skinConditionTextView.text = skinCondition

        adapter = RecomendedProductAdapter(emptyList()) { product ->
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(ProductDetailActivity.PRODUCT_DETAIL, product)
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        skinCondition?.let {
            resultViewModel.fetchRecommendedProducts(it)
        } ?: Toast.makeText(requireContext(), "No skin condition provided", Toast.LENGTH_SHORT).show()

        resultViewModel.recommendedProducts.observe(viewLifecycleOwner) { products ->
            Log.d("ResultFragment", "Products: ${products.size}")
            setupRecyclerView(products)
        }
    }

    private fun setupRecyclerView(products: List<GetAllBarangResponseItem>) {
        adapter = RecomendedProductAdapter(products) { product ->
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(ProductDetailActivity.PRODUCT_DETAIL, product)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(skinCondition: String): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString("SKIN_CONDITION", skinCondition)
            fragment.arguments = args
            return fragment
        }
    }
}