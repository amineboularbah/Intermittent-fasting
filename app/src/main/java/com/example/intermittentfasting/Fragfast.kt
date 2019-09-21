package com.example.intermittentfasting

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.frag_fast.*
import androidx.lifecycle.ViewModelProviders
import android.app.Dialog
import android.view.animation.AnimationUtils
import android.widget.*
import java.util.concurrent.TimeUnit
class Fragfast : Fragment() {
    private var viewModel: SharedViewModel? = null
    private var message : String ="0"
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return  inflater.inflate(R.layout.frag_fast,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)

        twelvehours.setOnClickListener{
            animation(twelvehours)
            if(message == "0"){
                message = 60000.toString() //4.32e+7 = 43200000L = 12 hours.
                viewModel!!.setText(message)
                saveValuetoSP()
                transFragTimer()
            }else{
                Toast.makeText( context,"Make sure you cancel the selected fast before you choose a new one !", Toast.LENGTH_SHORT).show()
                transFragTimer()
            }
        }
        sixteenhours.setOnClickListener{
            animation(sixteenhours)
            if(message == "0"){
            message = 57600000L.toString() //5.76e+7 = 57600000L = 16 hours.
            viewModel!!.setText(message)
                saveValuetoSP()
                transFragTimer()
            }else{
                Toast.makeText( context,"Make sure you cancel the selected fast before you choose a new one !", Toast.LENGTH_SHORT).show()
                transFragTimer()
        }
        }
        twentyhours.setOnClickListener{
            animation(twentyhours)
            if(message == "0"){
                message = 72000000L.toString() //7.2e+7 = 72000000L = 20 hours.
                viewModel!!.setText(message)
                saveValuetoSP()
                transFragTimer()
            }else{
                Toast.makeText( context,"Make sure you cancel the selected fast before you choose a new one !", Toast.LENGTH_SHORT).show()
                transFragTimer()
            }

        }
        twentyfourhours.setOnClickListener{
            animation(twentyfourhours)

            if(message == "0"){
                message = 86400000L.toString() //8.64e+7 = 86400000L = 24 hours.
                viewModel!!.setText(message)
                transFragTimer()
                saveValuetoSP()
            }else{
                Toast.makeText( context,"Make sure you cancel the selected fast before you choose a new one !", Toast.LENGTH_SHORT).show()
                transFragTimer()
            }
        }
        selecthours.setOnClickListener{
            animation(selecthours)
            if(message == "0"){
                showHoursPicker()
            }else{
                Toast.makeText( context,"Make sure you cancel the selected fast before you choose a new one !", Toast.LENGTH_SHORT).show()
                transFragTimer()
            }
        }
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel!!.getText().observe(viewLifecycleOwner,
            Observer<CharSequence> { charSequence -> message = charSequence.toString() })
    }
    private fun showHoursPicker(){
        val d = Dialog(this.activity!!)
        d.setTitle("NumberPicker")
        d.setContentView(R.layout.hourspicker)
        val b1 = d.findViewById(R.id.setbtn) as Button
        val b2 = d.findViewById(R.id.cnclbrn) as Button
        val np = d.findViewById(R.id.numberPicker1) as NumberPicker
        np.maxValue = 100
        np.minValue = 0
        np.wrapSelectorWheel = false
        np.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to text view
            selecthours.text = "Selected Hours : $newVal"
        }
        b1.setOnClickListener {
                selecthours.text = "Selected Hours : ${np.value}"
                message = TimeUnit.HOURS.toMillis(np.value.toLong()).toString()
                viewModel!!.setText(message)
                transFragTimer()
                saveValuetoSP()
                d.dismiss()
        }
        b2.setOnClickListener {
                d.dismiss()
        }
        d.show()
    }
    private fun transFragTimer(){
        val fragreplace : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragreplace.replace(R.id.frag,Fragtimer()).commit()
        val botnav: BottomNavigationView = activity!!.findViewById(R.id.bott_nav)
        botnav.selectedItemId= R.id.nav_timer
    }
    private fun saveValuetoSP(){
        val sharedPreferences =this.activity!!.getSharedPreferences("fasthistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("Fasthours",message.toLong())
        editor.apply()
    }
    private fun animation(button: TextView){
        val myAnim = AnimationUtils.loadAnimation(this.activity, R.anim.bouncef)
        val interpolator = Animation(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
    }

}
