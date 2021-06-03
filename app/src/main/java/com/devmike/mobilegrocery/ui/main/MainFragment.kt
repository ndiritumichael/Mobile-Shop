package com.devmike.mobilegrocery.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment

import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devmike.mobilegrocery.R
import com.devmike.mobilegrocery.adapters.ProductAdapter
import com.devmike.mobilegrocery.databinding.MainFragmentBinding
import com.devmike.mobilegrocery.models.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private val binding:MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var productAdapter : ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
      /*  val toolbar =binding.toolbar
        setHasOptionsMenu(true)
      //  o0
        toolbar.inflateMenu(R.menu.cart_menu)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_cart ->{
                    Log.d("fragment","Item clicked")
                    true
                }  else -> {
                super.onOptionsItemSelected(it)
            }

            }
        }
*/



        productAdapter = ProductAdapter{ product ->
            navigate(product)
        }
        binding.itemsRv.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.itemsRv.adapter = productAdapter
        viewModel.allProducts.observe(viewLifecycleOwner){productsList ->

            productAdapter.submitList(productsList)

        }



    }

    private fun navigate(product: Product) {
binding.root.findNavController().navigate(MainFragmentDirections.actionMainFragmentToProductsDetails2(product))

    }
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cart_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.action_cart ->{
                Log.d("fragment","Item clicked")
                true
            }  else -> {
                return super.onOptionsItemSelected(item)
            }

        }
    }*/


}