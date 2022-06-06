package com.boreal.puertocorazon.payments.ui.addcard

import android.webkit.CookieManager
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.component.dialogs.blurdialog.CUBlurDialogBinding
import com.boreal.commonutils.extensions.*
import com.boreal.commonutils.globalmethod.getDeviceId
import com.boreal.puertocorazon.core.utils.*
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.payments.R
import com.boreal.puertocorazon.uisystem.databinding.PcQuestionDialogBinding
import io.conekta.conektasdk.Conekta
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


fun PCAddCardFragment.initElements() {
    binding.apply {
        KeyboardVisibilityEvent.setEventListener(
            requireActivity()
        ) {
            btnReady.showIf(!it)
            if (txtNumberCard.hasFocus()) {
                showOrHide()
            }
        }
        txtNumberCard.setOnFocusChangeListener { _, _ ->
            showOrHide()
        }

        btnBack.onClick {
            onFragmentBackPressed()
        }

        btnSave.onClick {
            if (mainViewModel.getShoppingList().isEmpty()) {
                showToast("No hay articulos en el carrito de compra")
                return@onClick
            }
            if (mainViewModel.getNameUser().isEmpty() && cardValid()) {
                CUBlurDialogBinding<PcQuestionDialogBinding>(
                    layout = R.layout.pc_question_dialog,
                    callback = { binding, dialogBlur ->
                        binding.apply {
                            txtTitle.text = "¿El nombre está vacio, es tu mismo nombre de tarjeta?"
                            txtMessage.text = "Si, es mi mismo nombre"
                            txtBtnCancel.text = getString(R.string.cancelar)
                            txtBtnContinue.text = "Continuar"
                            btnCancel.onClick {
                                dialogBlur.dismissAllowingStateLoss()
                            }
                            btnContinue.onClick {
                                mainViewModel.requestPayment(
                                    nameCard = txtNameCard.onlyText(),
                                    aliasCard = txtAlias.onlyText(),
                                    ConektaCardModel(
                                        numberCard = txtNumberCard.onlyText().onlyCardNumber(),
                                        nameCard = txtNameCard.onlyText(),
                                        cvc = tvCvv.onlyText(),
                                        exp_month = tvMonthCard.onlyText(),
                                        exp_year = tvYearCard.onlyText()
                                    )
                                )
                                dialogBlur.dismissAllowingStateLoss()
                            }
                        }
                    },
                    cancelable = true
                ).also {
                    it.show(getSupportFragmentManager(), "dialog_question")
                }
            } else if (cardValid()) {
                mainViewModel.requestPayment(
                    nameCard = mainViewModel.getNameUser(),
                    aliasCard = txtAlias.onlyText(),
                    ConektaCardModel(
                        numberCard = txtNumberCard.onlyText().onlyCardNumber(),
                        nameCard = txtNameCard.onlyText(),
                        cvc = tvCvv.onlyText(),
                        exp_month = tvMonthCard.onlyText(),
                        exp_year = tvYearCard.onlyText()
                    )
                )
            } else {
                changeText("Revisa los datos de la tarjeta")
            }
        }
    }
    initOnChangeListener()
}

fun PCAddCardFragment.showOrHide() {
    binding.apply {
        if (txtNumberCard.hasFocus() && KeyboardVisibilityEvent.isKeyboardVisible(requireActivity())) {
            roundableTitle.hideView()
            lblNames.hideView()
            lblApMaterno.hideView()
            roundableNameCard.hideView()
            val set = ConstraintSet()
            set.clone(containerNewCard)
            set.connect(
                R.id.lblNumberCard,
                ConstraintSet.TOP,
                R.id.containerHomeImage,
                ConstraintSet.BOTTOM,
                40
            )
            set.applyTo(containerNewCard)
        } else {
            roundableTitle.showView()
            lblNames.showView()
            lblApMaterno.showView()
            roundableNameCard.showView()
            val set = ConstraintSet()
            set.clone(containerNewCard)
            set.connect(
                R.id.lblNumberCard,
                ConstraintSet.TOP,
                R.id.roundableNameCard,
                ConstraintSet.BOTTOM,
                24
            )
            set.applyTo(containerNewCard)
        }
    }
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
        return txtNameCard.onlyText().isNotEmpty() && txtNameCard.onlyText().length > 5 &&
                txtNumberCard.onlyText().validCardNumber() &&
                tvMonth.onlyText().validMonth() &&
                tvYear.onlyText().validYear() &&
                tvCvv.onlyText().validCvv()
    }
}

fun PCAddCardFragment.initOnChangeListener() {
    binding.apply {
        txtAlias.doAfterTextChanged {
            it?.let {
                tvAliasCard.text = it.onlyText().ifEmpty { "Alias" }
                changeImageCorrect1.notInvisibleIf(
                    it.onlyText().isNotEmpty() && it.onlyText().length > 2
                )
            }
        }
        txtNameCard.doAfterTextChanged {
            it?.let {
                tvNameCard.text = it.onlyText().ifEmpty { "Nombre" }
                changeImageCorrect2.notInvisibleIf(
                    it.onlyText().isNotEmpty() && it.onlyText().length > 5
                )
            }
        }
        txtNumberCard.doAfterTextChanged {
            it?.let {
                if (it.toString().endsWith(" ")) return@doAfterTextChanged
                if (it.toString().length == 4 || it.toString().length == 11 || it.toString().length == 18) {
                    txtNumberCard.setText(
                        StringBuilder(it.toString()).insert(it.toString().length, " - ").toString()
                    )
                    txtNumberCard.setSelection(
                        txtNumberCard.text.toString().length
                    )
                } else if (it.toString().isNotEmpty() && it.toString().last() == '-') {
                    txtNumberCard.setText(
                        it.toString().substring(0, it.toString().length - 3)
                    )
                    txtNumberCard.setSelection(
                        txtNumberCard.text.toString().length
                    )
                }

                val cardNumber = it.onlyCardNumber()
                if (cardNumber.isNotEmpty() && cardNumber.length < 5) {
                    txtNumberCardOneSegment.text = cardNumber
                    txtNumberCardTwoSegment.text = "0000"
                    txtNumberCardThreeSegment.text = "0000"
                    txtNumberCardFourSegment.text = "0000"
                } else if (cardNumber.length in 5..8) {
                    txtNumberCardTwoSegment.text =
                        cardNumber.substring(IntRange(4, cardNumber.length - 1))
                    txtNumberCardThreeSegment.text = "0000"
                } else if (cardNumber.length in 9..12) {
                    txtNumberCardThreeSegment.text =
                        cardNumber.substring(IntRange(8, cardNumber.length - 1))
                    txtNumberCardFourSegment.text = "0000"
                } else if (cardNumber.length in 13..16) {
                    txtNumberCardFourSegment.text =
                        cardNumber.substring(IntRange(12, cardNumber.length - 1))
                } else if (cardNumber.isEmpty()) {
                    txtNumberCardOneSegment.text = "0000"
                    txtNumberCardTwoSegment.text = "0000"
                    txtNumberCardThreeSegment.text = "0000"
                    txtNumberCardFourSegment.text = "0000"
                }
                changeImageCorrect3.notInvisibleIf(
                    it.validCardNumber()
                )
            }
        }
        tvMonth.doAfterTextChanged {
            it?.let {
                tvMonthCard.text =
                    if (it.onlyText().length == 1) "0${it.onlyText()}" else it.onlyText()
                changeImageCorrect4.notInvisibleIf(
                    it.validMonth() && tvYear.text.toString().validYear()
                )
            }
        }
        tvYear.doAfterTextChanged {
            it?.let {
                tvYearCard.text = it.onlyText()
                changeImageCorrect4.notInvisibleIf(
                    it.validYear() && tvMonth.text.toString().validMonth()
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