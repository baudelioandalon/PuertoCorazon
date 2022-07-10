package com.boreal.puertocorazon.addevent.ui.details

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.extensions.onClick
import com.boreal.commonutils.extensions.showToast
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.component.maputils.AMapUtilityActivity
import com.boreal.puertocorazon.core.domain.entity.event.PCScheduleModel
import com.boreal.puertocorazon.core.utils.*
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


fun PCDetailsAddEventFragment.initElements() {
    binding.apply {

        btnOpenMap.onClick {
            responseLocation.launch(Intent(requireContext(), AMapUtilityActivity::class.java))
        }

        val cal = Calendar.getInstance()
        var tempInitialDate =
            if (viewModel.isScheduleValid()) viewModel.getSchedule()[0].startTime else Timestamp.now()
        var tempEndingDate =
            if (viewModel.isScheduleValid()) viewModel.getSchedule()[0].endTime else Timestamp.now()
        var tempStartHour = Timestamp.now()
        var tempEndHour = Timestamp.now()
        tvInitialDate.text = getToday(tempInitialDate)
        tvDateEnding.text = getToday(tempEndingDate)
        tvStartHour.text = getHourAMPM(timestamp = tempInitialDate)
        tvEndHour.text =
            if (viewModel.isScheduleValid()) getHourAMPM(timestamp = viewModel.getSchedule()[0].endTime) else getEndHour()

        roundableInitialDate.onClick {
            DatePickerDialog(
                requireContext(), R.style.DatePickerTheme,
                { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    tvInitialDate.text = cal.time.time.toFormat()
                    tempInitialDate = cal.time.time.toTimeStamp()
                },
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        roundableEndingDate.onClick {
            DatePickerDialog(
                requireContext(), R.style.DatePickerTheme,
                { _, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    tvDateEnding.text = cal.time.time.toFormat()
                    val timestampEnd = cal.time.time.toTimeStamp()
                    tempEndingDate = timestampEnd
                },
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        roundableStartHour.onClick {
            TimePickerDialog(
                requireContext(), R.style.DatePickerTheme,
                { _, hourOfDay, minute ->
                    val startHour =
                        "${if (hourOfDay.toString().length == 1) "0$hourOfDay" else hourOfDay}:${if (minute.toString().length == 1) "0$minute" else minute} ${if (hourOfDay > 11) "P.M." else "A.M."}"
                    tvStartHour.text = startHour
                    tempStartHour = tempInitialDate.setHour(startHour)
                    tempStartHour.getFormat("EEEE dd MMM yy HH:mm:ss").log("DATE_START")
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
            ).show()
        }
        roundableEndHour.onClick {
            TimePickerDialog(
                requireContext(), R.style.DatePickerTheme,
                { _, hourOfDay, minute ->
                    val endHour =
                        "${if (hourOfDay.toString().length == 1) "0$hourOfDay" else hourOfDay}:${if (minute.toString().length == 1) "0$minute" else minute} ${if (hourOfDay > 11) "P.M." else "A.M."}"
                    tvEndHour.text = endHour
                    tempEndHour = tempEndingDate.setHour(endHour)
                    tempEndHour.getFormat("EEEE dd MMM yy HH:mm:ss").log("DATE_END")
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
            ).show()
        }

        btnSave.onClick {
            tempInitialDate = tempInitialDate.setHour(tvStartHour.onlyText())
            tempEndingDate = tempEndingDate.setHour(tvEndHour.onlyText())
            if (Timestamp.now() greaterThan tempInitialDate) {
                lifecycleScope.launch {
                    tvErrorMessage.text = "La hora inicial tiene que ser mayor a la actual"
                    delay(3000)
                    tvErrorMessage.text = ""
                }
                return@onClick
            }
            if (tvInitialDate.onlyText() == tvDateEnding.onlyText()) {
                if (tempInitialDate greaterThan tempEndingDate) {
                    lifecycleScope.launch {
                        tvErrorMessage.text = "La fecha inicial es mayor a la fecha final"
                        delay(3000)
                        tvErrorMessage.text = ""
                    }
                } else {
                    val eventHours = tempEndingDate hoursBetweenDays tempInitialDate
                    viewModel.setSchedule(
                        listOf(
                            PCScheduleModel(
                                idTime = 1,
                                startTime = tempInitialDate,
                                endTime = tempEndingDate
                            )
                        )
                    )
                    val minutes = tempEndingDate.minutesBetweenDays(tempInitialDate, true) % 60
                    showToast(
                        "Tu evento durará $eventHours ${if (eventHours > 1) "horas" else "${if (eventHours == 0L) "horas" else "hora"}"} ${
                            if (minutes != 0L) {
                                " con $minutes ${if (minutes > 1) " minutos" else " minuto"}"
                            } else {
                                ""
                            }
                        }"
                    )
                    onFragmentBackPressed()
                }
            } else {
                val eventHours = tempEndingDate hoursBetweenDays tempInitialDate
                if (eventHours > 24) {
                    lifecycleScope.launch {
                        tvErrorMessage.text = "Tu evento no puede durar mas de 24 horas"
                        delay(3000)
                        tvErrorMessage.text = ""
                    }
                } else {
                    viewModel.setSchedule(
                        listOf(
                            PCScheduleModel(
                                idTime = 1,
                                startTime = tempInitialDate,
                                endTime = tempEndingDate
                            )
                        )
                    )
                    val minutes = tempEndingDate.minutesBetweenDays(tempInitialDate, true) % 60
                    showToast(
                        "Tu evento durará $eventHours ${if (eventHours > 1) "horas" else "${if (eventHours == 0L) "horas" else "hora"}"} ${
                            if (minutes != 0L) {
                                " con $minutes ${if (minutes > 1) " minutos" else " minuto"}"
                            } else {
                                ""
                            }
                        }"
                    )
                    onFragmentBackPressed()
                }
            }
        }

    }
}