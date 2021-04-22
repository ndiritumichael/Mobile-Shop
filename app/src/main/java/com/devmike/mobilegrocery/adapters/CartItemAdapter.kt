package com.devmike.mobilegrocery.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

import com.devmike.mobilegrocery.databinding.CartItemBinding
import com.devmike.mobilegrocery.models.OrdersItem

class CartItemAdapter (val deleteOrder :(Int) -> Unit): ListAdapter<OrdersItem,CartItemAdapter.CartViewHolder>(CartItemDiffUtil()) {

   inner class CartViewHolder(val binding: CartItemBinding):RecyclerView.ViewHolder(binding.root),View.OnClickListener {

       init {
binding.apply {
root.setOnClickListener(this@CartViewHolder)
}
       }
       fun bind(item: OrdersItem) {
binding.apply {
    name.text = item.product!!.name
dif.setOnClickListener {
    Log.d("adapter","Adapter Stuff Clicked")

 deleteOrder(item.orderItemId!!)

}
    """${item.quantity} Kg""".also { quantity.text = it }
    price.text = item.unitPrice.toString()
    binding.image.load(item.product!!.image){
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}

       }

       override fun onClick(v: View?) {

       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
      return CartViewHolder(
          CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.binding.dif.setOnClickListener {
            deleteOrder(getItem(position).orderItemId!!)
        }
     holder.bind(getItem(position))
    }
}

class CartItemDiffUtil:DiffUtil.ItemCallback<OrdersItem>() {
    override fun areItemsTheSame(oldItem: OrdersItem, newItem: OrdersItem): Boolean {
       return oldItem.orderItemId == newItem.orderItemId
    }

    override fun areContentsTheSame(oldItem: OrdersItem, newItem: OrdersItem): Boolean {
       return oldItem== newItem
    }


    interface onDelItemListener{

       fun onItemClicked(id:Int)
    }

}
