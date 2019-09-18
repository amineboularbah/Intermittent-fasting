package com.example.intermittentfasting
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.DatePicker
import android.widget.Toast
import android.widget.EditText
import android.widget.TextView
import java.util.*
import java.text.SimpleDateFormat

class SaveWeight: AppCompatDialogFragment(){
    private var weight:String?=null
    private var date:String?=null
    private var mkilos: EditText? = null
    private var mDate: TextView? = null
    @SuppressLint("InflateParams", "SimpleDateFormat")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_fragment, null)
        builder.setView(view)
        .setPositiveButton("Save"){ _: DialogInterface, _: Int ->
            date = mDate!!.text.toString()
            if (mkilos!!.text.isNotEmpty()){
                weight = mkilos!!.text.toString()
            }else {
                Toast.makeText(this .activity,"Add Info", Toast.LENGTH_SHORT).show()
        }


        }
        .setNegativeButton("Cancel"){ _: DialogInterface, _: Int ->
            dismiss()
        }
            .setNeutralButton("Change date"){ _: DialogInterface, _: Int ->
                if (mkilos!!.text.isNotEmpty()){
                    weight = mkilos!!.text.toString()
                }
                val sharedPreferences =context!!.getSharedPreferences("weightDatePicked", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("WeightPicked",weight)
                editor.apply()
                val newFragment =   ExDatePicker()
                newFragment.show(fragmentManager!!, "datePicker")
            }
        mkilos = view.findViewById(R.id.kilos)
        mDate = view.findViewById(R.id.datetx)
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        val formattedDate = df.format(c)
        val sharedPreferences =context!!.getSharedPreferences("weightDatePicked", Context.MODE_PRIVATE)
        date = sharedPreferences.getString("DatePicked",formattedDate)
        weight = sharedPreferences.getString("WeightPicked",weight)
        mDate!!.text = date
        mkilos!!.setText(weight)
        return builder.create()
    }
}