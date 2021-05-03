package com.devmike.mobilegrocery.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmike.mobilegrocery.BuildConfig
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
import org.json.JSONObject
import java.math.BigDecimal


const val PAYPAL_REQUEST_CODE = 2021

object Config {
    val payconfig: PayPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(BuildConfig.API_KEY)
}

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private val viewmodel: MainViewModel by viewModels()
    private val binding: CartActivityBinding by viewBinding()
    private lateinit var adapter: CartItemAdapter
    var checkoutAmount = 0.0
    private val TAG = CartActivity::class.java.name


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = Intent(this, PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.payconfig)
        startService(intent)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
        adapter = CartItemAdapter { id ->
            deleteItem(id)
        }
        setUpUI()


    }

    private fun setUpUI() {
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        binding.cartRecycler.adapter = adapter
        viewmodel.allOrdersItem.observe(this) { list ->
            adapter.submitList(list)
        }
        viewmodel.allOrdersItem.observe(this) { ordersItem ->
            if (ordersItem.isEmpty()){

                checkoutAmount = 0.0
            }
            binding.clearCart.text = "Clear Cart"
            totalCost(ordersItem)
        }
        binding.checkout.setOnClickListener {
            if (checkoutAmount==0.0){

                Toast.makeText(this,"Your cart is empty please add some items",Toast.LENGTH_SHORT).show()
            }
            checkOut()
        }
        binding.clearCart.setOnClickListener {
            viewmodel.clearcart()
            binding.clearCart.text = "Cart is Clear"
            Toast.makeText(this, "Your Cart Has Been Cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkOut() {
        if (checkoutAmount != 0.0) {


            val payment = PayPalPayment(
                BigDecimal(checkoutAmount), "USD", "New Order",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            val intent = Intent(this, PaymentActivity::class.java)


            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, Config.payconfig)

            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)

            startActivityForResult(intent, PAYPAL_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PAYPAL_REQUEST_CODE && resultCode == RESULT_OK) {
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
                    viewmodel.clearcart()
                    binding.confirmed.root.visibility = View.VISIBLE
                    binding.confirmed.amount.text = """USD: ${payment.amount.toString()}"""
                    binding.confirmed.paymentID.text = "ID of Payment: ${response.response.id}"
                    binding.confirmed.timeofpay.text = "Time of Payment : ${response.response.create_time}"




                } catch (e: JSONException) {
                    Log.e(TAG, "an extremely unlikely failure occurred: ", e)
                }

            }


        } else if (requestCode == PAYPAL_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                this,
                "You cancelled the transaction ,Please try again " ,
                Toast.LENGTH_LONG
            ).show()


        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(
                this,
                "An invalid Payment or PayPalConfiguration was submitted.Please restart application",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Something Went wrong Please Try Again", Toast.LENGTH_LONG)
                .show()
        }


    }

    private fun deleteItem(id: Int) {

        viewmodel.deleteOrder(id)

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


    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }


}

