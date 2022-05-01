package com.boreal.puertocorazon.addevent.ui.main

import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.extensions.onClick
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.utils.onlyText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun PCMainAddEventFragment.initElements() {
    binding.apply {

        tvTitle.setText(addEventViewModel.getEventTitle())
        tvSubtitle.setText(addEventViewModel.getEventSubtitle())
        tvDescription.setText(addEventViewModel.getEventDescription())

        btnSave.onClick {
            val validations = arrayListOf(
                Pair(tvDescription.onlyText().isEmpty()) {
                    tvDescription.doAfterTextChanged {
                        roundableDescription.strokeLineColor =
                            ContextCompat.getColor(requireContext(), R.color.blue_edittext)
                    }
                    showKeyboard(tvDescription)
                    roundableDescription.strokeLineColor =
                        ContextCompat.getColor(requireContext(), R.color.red_400)
                },
                Pair(tvSubtitle.onlyText().isEmpty()) {
                    tvSubtitle.doAfterTextChanged {
                        roundableSubtitle.strokeLineColor =
                            ContextCompat.getColor(requireContext(), R.color.blue_edittext)
                    }
                    showKeyboard(tvSubtitle)
                    roundableSubtitle.strokeLineColor =
                        ContextCompat.getColor(requireContext(), R.color.red_400)
                },
                Pair(tvTitle.onlyText().isEmpty()) {
                    tvTitle.doAfterTextChanged {
                        roundableTitle.strokeLineColor =
                            ContextCompat.getColor(requireContext(), R.color.blue_edittext)
                    }
                    showKeyboard(tvTitle)
                    roundableTitle.strokeLineColor =
                        ContextCompat.getColor(requireContext(), R.color.red_400)
                }
            ).reversed()

            if (validations[0].first) {
                lifecycleScope.launch {
                    tvErrorMessage.text = "No has ingresado un titulo"
                    validations[0].second.invoke()
                    delay(3000)
                    tvErrorMessage.text = ""
                }
                return@onClick
            }
            if (validations[1].first) {
                lifecycleScope.launch {
                    tvErrorMessage.text = "No has ingresado un subtitulo"
                    validations[1].second.invoke()
                    delay(3000)
                    tvErrorMessage.text = ""
                }
                return@onClick
            }
            if (validations[2].first) {
                lifecycleScope.launch {
                    tvErrorMessage.text = "No has ingresado una descripción"
                    validations[2].second.invoke()
                    delay(3000)
                    tvErrorMessage.text = ""
                }
                return@onClick
            }

            if (validations.indexOfFirst { it.first } == -1) {
                addEventViewModel.setMainData(
                    tvTitle.onlyText(),
                    tvSubtitle.onlyText(),
                    tvDescription.onlyText()
                )
                onFragmentBackPressed()
            }
        }

    }
}