package com.capstone.skinifier.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.skinifier.view.productDetail.ProductDetailActivity
import com.capstone.skinifier.view.productDetail.ProductDetailActivity.Companion.PRODUCT_DETAIL
import com.capstone.skinifier.R
import com.capstone.skinifier.data.model.ProductModelItem
import com.capstone.skinifier.data.util.SkinType
import com.capstone.skinifier.databinding.FragmentProductBinding
import com.capstone.skinifier.ui.add_product.AddProductActivity
import com.capstone.skinifier.view.viewModelFactory.ProductViewModelFactory

class ProductFragment : Fragment() {

    private val viewModel: ProductViewModel by viewModels{ ProductViewModelFactory.getInstance(requireContext())}
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var binding: FragmentProductBinding


    override fun onResume() {
        super.onResume()
        viewModel.fetchAllProducts()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.fetchAllProducts()
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_product)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        productAdapter = ProductAdapter{
            navigateToDetail(it)
        }
        recyclerView.adapter = productAdapter

        viewModel.products.observe(viewLifecycleOwner){
            productAdapter.submitData(it)
        }

        with(binding) {
            btnAdd.setOnClickListener {
                navigateToAdd()
            }
            buttonOily.setOnClickListener {
                viewModel.fetchProductsByType(SkinType.OilySkin)
            }
            buttonDry.setOnClickListener {
                viewModel.fetchProductsByType(SkinType.DrySkin)
            }
            buttonNormal.setOnClickListener {
                viewModel.fetchProductsByType(SkinType.NormalSkin)
            }
            buttonAcne.setOnClickListener {
                viewModel.fetchProductsByType(SkinType.AcneSkin)
            }


            searchBar.setOnEditorActionListener { textView, i, _ ->
                if (textView.text == ""){
                    viewModel.fetchAllProducts()
                }
                when(i) {
                    EditorInfo.IME_ACTION_DONE -> {
                        viewModel.fetchProductsByName(textView.text.toString())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun navigateToDetail(product: ProductModelItem) {
        Intent(requireActivity(), ProductDetailActivity::class.java).apply {
            putExtra(PRODUCT_DETAIL, product)
            startActivity(this)
        }
    }

    private fun navigateToAdd() {
        Intent(requireActivity(), AddProductActivity::class.java).apply {
            startActivity(this)
        }
    }
}
