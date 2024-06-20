package com.capstone.skinifier.ui.add_product

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.skinifier.R
import com.capstone.skinifier.data.util.ProductType
import com.capstone.skinifier.data.util.SkinType
import com.capstone.skinifier.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    
    private val viewModel: AddProductViewModel by viewModels {
        AddProductViewModelFactory.getInstance(this)
    }
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { handleGalleryImageSelection(uri) }
        }

    private var selectedSkinType = ""
    private var selectedProductType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val skinTypes = SkinType::class.sealedSubclasses.mapNotNull { it.objectInstance?.skinType }
        val productTypes = ProductType::class.sealedSubclasses.mapNotNull { it.objectInstance?.name }
        val skinTypeAdapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, skinTypes)
        val productTypeAdapter = ArrayAdapter(this, R.layout.dropdown_menu_popup_item, productTypes)
        binding.productTypeDropdown.setAdapter(productTypeAdapter)
        binding.skinTypeDropDown.setAdapter(skinTypeAdapter)


        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Optionally, set an item click listener
        binding.productTypeDropdown.setOnItemClickListener { parent, _, position, _ ->
            selectedProductType = parent.getItemAtPosition(position).toString()
        }

        binding.skinTypeDropDown.setOnItemClickListener { parent, _, position, _ ->
            selectedSkinType = parent.getItemAtPosition(position).toString()
        }

        viewModel.message.observe(this) {
            showMessage(it)
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                finish()
            }
        }


        with(binding) {
            imageAdd.setOnClickListener {
                openGallery()
            }
            imageButton.setOnClickListener {
                onBackPressed()
                finish()
            }

            uploadButton.setOnClickListener {
                val nama_brand = brandName.text.toString()
                val harga = priceField.text.toString()
                val no_hp = phoneField.text.toString()
                val bahan = ingredientField.text.toString()
                val jenis_produk = selectedProductType
                val nama_barang = productName.text.toString()
                val deskripsi = descriptionField.text.toString()
                val skin_type = selectedSkinType
                val domisili = domicileField.text.toString()

                if (areFieldsValid(nama_brand, harga, no_hp, bahan, jenis_produk, nama_barang, deskripsi, skin_type, domisili)) {
                    viewModel.uploadProduct(
                        nama_brand = nama_brand,
                        harga = harga,
                        bahan = bahan,
                        no_hp = no_hp,
                        jenis_produk = jenis_produk,
                        nama_barang = nama_barang,
                        deskripsi = deskripsi,
                        skin_type = skin_type,
                        domisili = domisili
                    )
                } else {
                    showMessage("Semua Harus Kolom Harus diisi")
                }

            }
        }

    }

    fun areFieldsValid(
        namaBrand: String,
        harga: String,
        noHp: String,
        bahan: String,
        jenisProduk: String,
        namaBarang: String,
        deskripsi: String,
        skinType: String,
        domisili: String
    ): Boolean {
        return namaBrand.isNotEmpty() &&
                harga.isNotEmpty() &&
                noHp.isNotEmpty() &&
                bahan.isNotEmpty() &&
                jenisProduk.isNotEmpty() &&
                namaBarang.isNotEmpty() &&
                deskripsi.isNotEmpty() &&
                skinType.isNotEmpty() &&
                domisili.isNotEmpty()
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun onImageSelected() {
        binding.plusImage.visibility = View.GONE
        binding.imageAdd.alpha = 1f
    }

    private fun handleGalleryImageSelection(uri: Uri) {
        binding.imageAdd.setImageURI(uri)
        onImageSelected()
        viewModel.setSelectedImage(uri, this)
    }

    private fun showMessage(q: String) {
        Toast.makeText(this, q, Toast.LENGTH_SHORT).show()
    }

}