package com.devmike.mobilegrocery.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmike.mobilegrocery.R
import com.devmike.mobilegrocery.adapters.ProductAdapter
import com.devmike.mobilegrocery.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding:MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var productAdapter : ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        productAdapter = ProductAdapter()
        binding.itemsRv.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.itemsRv.adapter = productAdapter
        viewModel.allProducts.observe(viewLifecycleOwner){productsList ->

            productAdapter.submitList(productsList)

        }



    }





}