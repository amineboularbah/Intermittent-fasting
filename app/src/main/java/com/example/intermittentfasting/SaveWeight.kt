package com.example.intermittentfasting
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.frag_progress.*
import java.util.*
import java.text.SimpleDateFormat

class SaveWeight: AppCompatDialogFragment(){
    private var weight:String?= "00"
    private var date:String?=null
    private var mkilos: EditText? = null
    private var mDate: TextView? = null
    var weightList: ArrayList<ExampleWeight> = ArrayList()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        loadData()
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.layout_fragment, null)
        builder.setView(view)
        .setPositiveButton("Save"){ _: DialogInterface, _: Int ->
            if (mkilos!!.text.isNotEmpty()){
                weight = mkilos!!.text.toString()
                date = mDate!!.text.toString()
                weightList.add(ExampleWeight(weight!! + "  KG / LB",date!!))
                saveData()
                weight = "00"
                restartProgFrag()
            }else{
                Toast.makeText(this .activity,"Add your weight", Toast.LENGTH_SHORT).show()
                dialogDate()
                builder.create()
            }
        }
            .setNegativeButton("Cancel"){ _: DialogInterface, _: Int ->
            dismiss()
            }
            .setNeutralButton("Change date"){ _: DialogInterface, _: Int ->
                if (mkilos!!.text.isNotEmpty()){
                    weight = mkilos!!.text.toString()
                    val sharedPreferences =context!!.getSharedPreferences("weightDatePicked", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("WeightPicked",weight)
                    editor.apply()
                }
                val newFragment =   ExDatePicker()
                newFragment.show(fragmentManager!!, "datePicker")
            }
        mkilos = view.findViewById(R.id.kilos)
        mDate = view.findViewById(R.id.datetx)
        dialogDate()
        return builder.create()
    }
    private fun saveData() {
        val sharedPreferences =context!!.getSharedPreferences("weightHistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(weightList)
        editor.putString("weightList", json)
        editor.apply()
    }
    private fun loadData() {
        val sharedPreferences = context!!.getSharedPreferences("weightHistory", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("weightList", null)
        val type = object : TypeToken<ArrayList<ExampleWeight>>() {
        }.type
        if(json != null) {
            weightList = gson.fromJson(json, type)
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun dialogDate(){
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy")
        val formattedDate = df.format(c)
        val sharedPreferences =context!!.getSharedPreferences("weightDatePicked", Context.MODE_PRIVATE)
        date = sharedPreferences.getString("DatePicked",formattedDate)
        weight = sharedPreferences.getString("WeightPicked",weight)
        mDate!!.text = date
        mkilos!!.setText(weight)
    }
    private fun restartProgFrag(){
        val fragreplace : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragreplace.replace(R.id.frag,FragProgress()).commit()
        val botnav: BottomNavigationView = activity!!.findViewById(R.id.bott_nav)
        botnav.selectedItemId= R.id.nav_weight
    }
}