package com.boreal.puertocorazon.addevent.ui.main

import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.boreal.commonutils.extensions.setOnSingleClickListener
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.utils.onlyText

fun PCMainAddEventFragment.initElements() {
    binding.apply {

        tvTitle.setText(addEventViewModel.getEventTitle())
        tvSubtitle.setText(addEventViewModel.getEventSubtitle())
        tvDescription.setText(addEventViewModel.getEventDescription())

        btnSave.setOnSingleClickListener {
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
                tvErrorMessage.text = "No has ingresado un titulo"
                validations[0].second.invoke()
                return@setOnSingleClickListener
            }
            if (validations[1].first) {
                tvErrorMessage.text = "No has ingresado un subtitulo"
                validations[1].second.invoke()
                return@setOnSingleClickListener
            }
            if (validations[2].first) {
                tvErrorMessage.text = "No has ingresado una descripción"
                validations[2].second.invoke()
                return@setOnSingleClickListener
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