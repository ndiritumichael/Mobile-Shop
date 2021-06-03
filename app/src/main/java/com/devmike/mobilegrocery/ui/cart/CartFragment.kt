package com.devmike.mobilegrocery.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmike.mobilegrocery.BuildConfig
import com.devmike.mobilegrocery.R
import com.devmike.mobilegrocery.adapters.CartItemAdapter
import com.devmike.mobilegrocery.databinding.CartActivityBinding
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Payment
import com.devmike.mobilegrocery.models.PaypalResponse
import com.devmike.mobilegrocery.ui.main.MainViewModel
import com.google.gson.Gson
import com.paypal.android.sdk.payments.*
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


const val PAYPAL_REQUEST_CODE = 2021

object Config {
    val payconfig: PayPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(BuildConfig.API_KEY)
}

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.cart_activity) {
    private val mainViewModel: MainViewModel by viewModels()
    private val binding: CartActivityBinding by viewBinding()
    private lateinit var adapter: CartItemAdapter
    var checkoutAmount = 0.0
    private val TAG = "CartFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Cart"

        adapter = CartItemAdapter { id ->
            deleteItem(id)
        }
        setUpUI()

    }

    private fun setUpUI() {
        binding.cartRecycler.layoutManager = LinearLayoutManager(context)
        binding.cartRecycler.adapter = adapter

        mainViewModel.allOrdersItem.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            // Toast.makeText(this,"${list[99]}",Toast.LENGTH_SHORT).show()
        }
        mainViewModel.allOrdersItem.observe(viewLifecycleOwner) { ordersItem ->
            if (ordersItem.isEmpty()){

                checkoutAmount = 0.0
            }
            binding.clearCart.text = "Clear Cart"
            totalCost(ordersItem)
        }
        binding.checkout.setOnClickListener {
            if (checkoutAmount==0.0){

                Toast.makeText(context,"Your cart is empty please add some items", Toast.LENGTH_SHORT).show()
            }
            checkOut()
        }
        binding.clearCart.setOnClickListener {
            mainViewModel.clearcart()
            binding.clearCart.text = "Cart is Clear"
            Toast.makeText(context, "Your Cart Has Been Cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkOut() {
        if (checkoutAmount != 0.0) {


            val payment = PayPalPayment(
                BigDecimal(checkoutAmount), "USD", "New Order",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            val intent = Intent(context, PaymentActivity::class.java)



            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.payconfig)

            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

            startActivityForResult(intent, PAYPAL_REQUEST_CODE)
        }

    }
    fun totalCost(orderItemsList: List<OrdersItem>) {
        var total: Double = 0.0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity.toDouble() * orderItem.unitPrice!!
            checkoutAmount = total
        }
        if (total == 0.0) {
            binding.tvCartTotal.visibility = View.GONE
        }
        val totalItems = "Subtotal( ${orderItemsList.size} Order(s) ): $$total"
        binding.tvCartTotal.text = totalItems
    }

    private fun deleteItem(id: Int) {

        mainViewModel.deleteOrder(id)

    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PAYPAL_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            val confirm =
                data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)

            if (confirm != null) {
                try {


                    val response = Gson().fromJson(
                        confirm.toJSONObject().toString(4),
                        PaypalResponse::class.java
                    )



                    val payment = Gson().fromJson(
                        confirm.payment.toJSONObject().toString(4),
                        Payment::class.java
                    )
                    Log.d(TAG,"$payment")
                    mainViewModel.clearcart()
                    binding.checkout.visibility = View.GONE
                    binding.confirmed.root.visibility = View.VISIBLE
                    binding.confirmed.amount.text = """USD: ${payment.amount.toString()}"""
                    binding.confirmed.paymentID.text = "ID of Payment: ${response.response.id}"
                    binding.confirmed.timeofpay.text = "Time of Payment : ${response.response.create_time}"




                } catch (e: JSONException) {
                    Log.e(TAG, "an extremely unlikely failure occurred: ", e)
                }

            }


        } else if (requestCode == PAYPAL_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_CANCELED) {
            Toast.makeText(
                context,
                "You cancelled the transaction ,Please try again " ,
                Toast.LENGTH_LONG
            ).show()


        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(context,
                "An invalid Payment or PayPalConfiguration was submitted.Please restart application",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(context, "Something Went wrong Please Try Again", Toast.LENGTH_LONG)
                .show()
        }


    }




}