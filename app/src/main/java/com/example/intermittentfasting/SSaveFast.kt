package com.example.intermittentfasting
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_ssave_fast.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import android.app.NotificationManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView


class SSaveFast : AppCompatActivity() {
    private var fastlist:ArrayList<FastEx> = ArrayList()
    private var fasthours:Long = 0L
    private var comment:String = "No comment"
    private var img:Int ?= null
    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ssave_fast)

        //loading data also getting fast category from shared pref.
        loadData()
        val sharedPreferences =this.getSharedPreferences("fasthistory", Context.MODE_PRIVATE)
        fasthours = sharedPreferences.getLong("Fasthours", 0L)
        val hours = TimeUnit.MILLISECONDS.toHours(fasthours)
        val timeLeft = String.format(Locale.getDefault(), "%02d",hours)
        vFastCategory.text="Fast category :\n $timeLeft Hours"
        //Buttons click event.
        badimg.setOnClickListener{
            badtxt.setBackgroundResource(R.drawable.selected)
            goodtxt.setBackgroundColor(0)
            angrytxt.setBackgroundColor(0)
            img = R.drawable.sad
            animation(badimg)
        }
        angryimg.setOnClickListener{
            angrytxt.setBackgroundResource(R.drawable.selected)
            goodtxt.setBackgroundColor(0)
            badtxt.setBackgroundColor(0)
            img = R.drawable.angry
            animation(angryimg)
        }
        goodimg.setOnClickListener{
            goodtxt.setBackgroundResource(R.drawable.selected)
            angrytxt.setBackgroundColor(0)
            badtxt.setBackgroundColor(0)
            img = R.drawable.good
            animation(goodimg)
        }
        savebtn.setOnClickListener{
            btnAnimation(savebtn)
            comment = if(comtxt.text!!.isEmpty()){
                "No comment"
            }else {
                comtxt.text.toString()
            }
            if(img == null){
                Toast.makeText(this,"How do you feel ??",Toast.LENGTH_SHORT).show()
            }else{
                val date = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date())
                fastlist.add(FastEx(img!!, timeLeft+"Hours", comment,date))
                saveData()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText( this,"Fast saved !", Toast.LENGTH_SHORT).show()
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
        discardbtn.setOnClickListener {
            btnAnimation(discardbtn)
            val confirming = AlertDialog.Builder(this)
            confirming.setTitle("Discard fast")
            confirming.setMessage("Are you sure ?")
            confirming.setPositiveButton("DISCARD"){ _: DialogInterface, _: Int ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            confirming.setNegativeButton("No"){_: DialogInterface, _: Int ->
                Toast.makeText(this,"Fast not discarded !",Toast.LENGTH_SHORT).show()
            }
            val dialog = confirming.create()
            dialog.show()
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }
    }
    private fun saveData() {
        val sharedPreferences =this.getSharedPreferences("fasthistory", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(fastlist)
        editor.putString("fastlist", json)
        editor.apply()
    }
    private fun loadData() {
        val sharedPreferences = this.getSharedPreferences("fasthistory", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("fastlist", null)
        val type = object : TypeToken<ArrayList<FastEx>>() {
        }.type
        if(json != null) {
            fastlist = gson.fromJson(json, type)
        }
    }
    override fun onBackPressed() {
    }
    private fun animation(button: ImageView){
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bouncef)
        val interpolator = Animation(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
    }
    private fun btnAnimation(button: Button){
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bouncef)
        val interpolator = Animation(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
    }
}
