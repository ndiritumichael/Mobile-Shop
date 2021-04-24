package com.devmike.mobilegrocery.models

import android.os.Parcelable
import com.paypal.android.sdk.payments.PayPalItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaypalResponse(
    val response_type: String,
    val response: Response,
    val client:Client


):Parcelable{
@Parcelize
data class Response(
   val create_time : String,
   val id : String,
   val intent : String,
   val state : String
        ):Parcelable

    @Parcelize
    data class Client(
       val environment : String,
       val paypal_sdk_version : String,
        val platform : String,
       val product_name : String
    ):Parcelable



}

@Parcelize
data class Payment(
    val amount : Double,
val currency_code: String,
val short_description: String,
val intent: String
):Parcelable
