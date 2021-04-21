package com.devmike.mobilegrocery.ui.productDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.devmike.mobilegrocery.R
import com.devmike.mobilegrocery.databinding.ProductsDetailsFragmentBinding
import com.devmike.mobilegrocery.models.Product
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProductsDetailsFragment : Fragment(R.layout.products_details_fragment) {

    private val viewModel: ProductsDetailsViewModel by viewModels()
    private val binding :ProductsDetailsFragmentBinding by viewBinding()
    private val args:ProductsDetailsFragmentArgs by navArgs()
    private lateinit var  product : Product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product = args.product
        /*(activity as AppCompatActivity).supportActionBar?.title = product.name
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)*/
        binding.name.text = product.name.capitalize(Locale.getDefault())
        binding.description.text = product.description
        binding.images.load(product.image)
        binding.addToCart.setOnClickListener {
            viewModel.addCart(product)
        }
    }





}