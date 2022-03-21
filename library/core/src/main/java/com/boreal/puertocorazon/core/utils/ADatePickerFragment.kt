package com.boreal.puertocorazon.core.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.boreal.puertocorazon.core.R
import java.util.*

class ADatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val c2 = Calendar.getInstance()
        val year = c.get(Calendar.YEAR) - 18
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        c.add(Calendar.YEAR, -18)
        c2.set(1921, Calendar.JANUARY, 1)
        val picker =
            DatePickerDialog(
                activity as Context,
                R.style.ThemeOverlay_AppCompat_Dialog,
                this,
                year,
                month,
                day
            )
        // picker.datePicker.maxDate = c.timeInMillis
        picker.datePicker.maxDate = c.timeInMillis
        picker.datePicker.minDate = c2.time.time

        return picker
    }
}