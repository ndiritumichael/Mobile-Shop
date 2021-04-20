package com.devmike.mobilegrocery.utils

import androidx.recyclerview.widget.DiffUtil
import com.devmike.mobilegrocery.models.Product

class ProductsDiffUtil: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
       return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {


        return  oldItem == newItem

    }
}