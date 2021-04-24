package com.devmike.mobilegrocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devmike.mobilegrocery.databinding.ProductsadapterlayoutBinding
import com.devmike.mobilegrocery.models.Product
import com.devmike.mobilegrocery.utils.ProductsDiffUtil

class ProductAdapter (private val details :(Product)-> Unit): ListAdapter<Product, ProductAdapter.ProductsViewHolder>(ProductsDiffUtil()) {


   inner class ProductsViewHolder(private val binding: ProductsadapterlayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {

                productsImageview.load(product.image)
                productsNameTv.text =  product.name
                productsAmountTv.text = "USD: ${product.pricePerUnit}"

                root.setOnClickListener {
                    details(product)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductsadapterlayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}