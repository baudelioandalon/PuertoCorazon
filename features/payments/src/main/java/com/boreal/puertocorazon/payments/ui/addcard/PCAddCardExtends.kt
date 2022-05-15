package com.boreal.puertocorazon.payments.ui.addcard

import android.webkit.CookieManager
import android.webkit.WebView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.extensions.notInvisibleIf
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.globalmethod.getDeviceId
import com.boreal.puertocorazon.core.utils.*
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import io.conekta.conektasdk.Conekta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun PCAddCardFragment.initElements() {
    binding.apply {
        btnBack.onClick {
            onFragmentBackPressed()
        }

        btnSave.onClick {
            mainViewModel.requestPayment(
                aliasCard = txtAlias.onlyText(),
                ConektaCardModel(
                    numberCard = txtNameCard.onlyText(),
                    nameCard = txtNameCard.onlyText(),
                    cvc = txtCvv.onlyText(),
                    exp_month = "23",
                    exp_year = "22"
                )
            )
        }

        btnSave.onClick {
            if(cardValid()){
                showToast("Todo ok")
            }else{
                changeText("Revisa los datos de la tarjeta")
            }
        }
    }
    initOnChangeListener()
}

fun PCAddCardFragment.changeText(messageToShow: String) {
    binding.apply {
        lifecycleScope.launch {
            tvErrorMessage.text = messageToShow
            btnSave.isEnabled = false
            delay(3000)
            btnSave.isEnabled = true
            tvErrorMessage.clearText()
        }
    }
}

fun PCAddCardFragment.cardValid(): Boolean {
    binding.apply {
        return txtAlias.onlyText().isNotEmpty() && txtAlias.onlyText().length > 2 &&
                txtNameCard.onlyText().isNotEmpty() && txtNameCard.onlyText().length > 5 &&
                txtNameCard.onlyText().validCardNumber() &&
                tvMonth.onlyText().validMonth() &&
                tvYear.onlyText().validYear() &&
                tvCvv.onlyText().validCvv()
    }
}

fun PCAddCardFragment.initOnChangeListener() {
    binding.apply {
        txtAlias.doAfterTextChanged {
            it?.let {
                changeImageCorrect1.notInvisibleIf(
                    it.onlyText().isNotEmpty() && it.onlyText().length > 2
                )
            }
        }
        txtNameCard.doAfterTextChanged {
            it?.let {
                changeImageCorrect2.notInvisibleIf(
                    it.onlyText().isNotEmpty() && it.onlyText().length > 5
                )
            }
        }
        txtNumberCard.doAfterTextChanged {
            it?.let {
                changeImageCorrect3.notInvisibleIf(
                    it.validCardNumber()
                )
            }
        }
        tvMonth.doAfterTextChanged {
            it?.let {
                changeImageCorrect4.notInvisibleIf(
                    it.validMonth()
                )
            }
        }
        tvYear.doAfterTextChanged {
            it?.let {
                changeImageCorrect4.notInvisibleIf(
                    it.validYear()
                )
            }
        }
        tvCvv.doAfterTextChanged {
            it?.let {
                changeImageCorrect5.notInvisibleIf(
                    it.validCvv()
                )
            }
        }
    }
}

fun PCAddCardFragment.collectDevice() {
    val sessionId = getDeviceId()
    val publicKey = Conekta.getPublicKey()
    if (publicKey.isEmpty()) return

    var html = "<!DOCTYPE html><html><head></head><body style=\"background: blue;\">"
    html += "<script type=\"text/javascript\" src=\"https://conektaapi.s3.amazonaws.com/v0.5.0/js/conekta.js\" data-conekta-public-key=\"$publicKey\" data-conekta-session-id=\"$sessionId\"></script>"
    html += "</body></html>"

    val webView = WebView(requireActivity())

    CookieManager.getInstance().setAcceptCookie(true)
    CookieManager.getInstance()
        .setAcceptThirdPartyCookies(webView, true)

    webView.settings.javaScriptEnabled = true
    webView.settings.allowContentAccess = true
    webView.settings.databaseEnabled = true
    webView.settings.domStorageEnabled = true
    webView.loadDataWithBaseURL(
        "https://conektaapi.s3.amazonaws.com/v0.5.0/js/conekta.js",
        html, "text/html", "UTF-8", null
    )
}