package com.example.intermittentfasting

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import java.text.DateFormat
import java.util.*

class ExDatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var weightList:ArrayList<FastEx> = ArrayList()
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        val sharedPreferences =context!!.getSharedPreferences("weightDatePicked", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("DatePicked",currentDateString)
        editor.apply()
        val exampleDialog = SaveWeight()
        exampleDialog.show(fragmentManager!!, "example dialog")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }
}