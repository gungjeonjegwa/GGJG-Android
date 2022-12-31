package com.example.ggjg_andorid.utils

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.data.utils.removeDot
import com.example.ggjg_andorid.BuildConfig
import com.example.ggjg_andorid.viewmodel.PayViewModel
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.Payload

fun bootPayPayload(title: String, price: Double): Payload {
    return Payload().setApplicationId(BuildConfig.PAY_ID)
        .setOrderName(title)
        .setPg("이니시스")
        .setOrderId(PayViewModel.orderNumber)
        .setPrice(price + 3000)
        .setMethod(PayViewModel.payMethod)
}

fun bootPayCreate(
    supportFragmentManager: FragmentManager,
    applicationContext: Context,
    payload: Payload,
    onPayedEvent: () -> Unit,
) {
    Bootpay.init(supportFragmentManager, applicationContext)
        .setPayload(payload)
        .setEventListener(object : BootpayEventListener {
            override fun onCancel(data: String?) {
            }

            override fun onError(data: String?) {
            }

            override fun onClose(data: String?) {
                Bootpay.removePaymentWindow()
                val jsonParser = JsonParser()
                val result = ((jsonParser.parse(data) as JsonObject)["event"]).toString().removeDot()
                if (result == "done") {
                    onPayedEvent()
                }
            }

            override fun onIssued(data: String?) {
            }

            override fun onConfirm(data: String?): Boolean {
                return true
            }

            override fun onDone(data: String?) {
            }
        }).requestPayment()
}