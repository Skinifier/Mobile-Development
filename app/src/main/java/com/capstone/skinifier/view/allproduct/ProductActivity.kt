//package com.capstone.skinifier.ui.product
//
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.capstone.skinifier.data.pref.UserPreference
//import com.capstone.skinifier.data.pref.dataStore
//import com.capstone.skinifier.databinding.ActivityProductBinding
//import com.capstone.skinifier.view.allproduct.ProductAdapter
//import com.capstone.skinifier.view.viewModelFactory.ProductViewModelFactory
//import com.capstone.skinifier.data.repository.ProductRepository
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.launch
//
//class ProductActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityProductBinding
//    private lateinit var userPreference: UserPreference
//    private lateinit var productAdapter: ProductAdapter
//    private val productViewModel: ProductViewModel by viewModels {
//        ProductViewModelFactory(ProductRepository())
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityProductBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        userPreference = UserPreference.getInstance(dataStore)
//        productAdapter = ProductAdapter()
//
//        setupRecyclerView()
//        getUserSession()
//        observeViewModel()
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvProduct.apply {
//            layoutManager = LinearLayoutManager(this@ProductActivity)
//            adapter = productAdapter
//        }
//    }
//
//    private fun getUserSession() {
//        lifecycleScope.launch {
//            userPreference.getSession().collect { user ->
//                if (user != null) {
//                    productViewModel.fetchAllProducts(user.token)
//                }
//            }
//        }
//    }
//
//    private fun observeViewModel() {
//        productViewModel.products.observe(this) { products ->
//            products?.let {
//                productAdapter.submitList(it)
//            }
//        }
//    }
//}
