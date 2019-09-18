package com.example.intermittentfasting
import android.annotation.SuppressLint
import android.app.*
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.frag_timer.*
import java.util.*
import java.util.concurrent.TimeUnit
import android.content.Context.MODE_PRIVATE
import android.text.format.DateFormat
import android.content.Intent
import androidx.core.content.ContextCompat

@Suppress("UNREACHABLE_CODE", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")
@SuppressLint("SetTextI18n")
class  Fragtimer : Fragment() {
    //Variables declaration
    private var viewModel: SharedViewModel? = null
    private var mEndTime: Long = 0
    private var startTimeInMillis:Long =0L
    private var timeLeftInMillis =startTimeInMillis
    private var countDownTimer: CountDownTimer ? = null
    private var countDownTimer2: CountDownTimer ? = null
    private var timerRunning: Boolean = false
    private var endtime :Long = 0
    private var timeselected = false
    private var timelefttofast = 0L
    private var start =0L
    var i = 0
    private var secondsRemaining: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_timer,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nowbtn.setOnClickListener{
            whenbtn.visibility=View.INVISIBLE
            nowbtn.visibility=View.INVISIBLE
            Cancelbtn.visibility=View.VISIBLE
            if(startTimeInMillis != 0L){
                timeLeftInMillis=startTimeInMillis
                startTimer()
            }else {
                val fragreplace : FragmentTransaction = fragmentManager!!.beginTransaction()
                fragreplace.replace(R.id.frag, Fragfast()).commit()
                val botnav: BottomNavigationView = activity!!.findViewById(R.id.bott_nav)
                botnav.selectedItemId= R.id.nav_fast
                Toast.makeText( context,"You are not fasting !!\n Check fasts list and select a fasting category !", Toast.LENGTH_SHORT).show()
            }
        }
        whenbtn.setOnClickListener{
            if(startTimeInMillis !=0L){
                val c = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
                    whenbtn.visibility = View.INVISIBLE
                    Cancelbtn.visibility=View.VISIBLE
                    timeselected=true
                    c.set(Calendar.HOUR_OF_DAY,hour)
                    c.set(Calendar.MINUTE,minute)
                    c.set(Calendar.SECOND,0)
                    start=c.timeInMillis
                    timelefttofast = if (c.timeInMillis < System.currentTimeMillis() ) {
                        c.add(Calendar.DATE, 1)
                        c.timeInMillis - System.currentTimeMillis()
                    }else {
                        c.timeInMillis - System.currentTimeMillis()
                    }
                    timelefttofast()
                }
                TimePickerDialog(this.activity,timeSetListener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), DateFormat.is24HourFormat(this.activity)).show()
            }else {
                val fragreplace : FragmentTransaction = fragmentManager!!.beginTransaction()
                fragreplace.replace(R.
                    id.frag,Fragfast()).commit()
                val botnav: BottomNavigationView = activity!!.findViewById(R.id.bott_nav)
                botnav.selectedItemId= R.id.nav_fast }
        }
        Cancelbtn.setOnClickListener{
            stopService()
            whenbtn.visibility=View.VISIBLE
            nowbtn.visibility=View.INVISIBLE
            Cancelbtn.visibility=View.INVISIBLE
            prog.progress = 100
            whenbtn.text="See all fasts"
            timeselected=false
            startTimeInMillis=0L
            timelefttofast=0L
            timeLeftInMillis=startTimeInMillis
            timerRunning = false
            viewModel!!.setText(startTimeInMillis.toString())
            timer_count.text="Choose a fasting category"
            cancel()
            val prefs = this.activity!!.getSharedPreferences("IF prefs", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putLong("input", timeLeftInMillis)
            editor.apply()
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        viewModel!!.getText().observe(viewLifecycleOwner,
            Observer<CharSequence> { charSequence -> startTimeInMillis = charSequence.toString().toLong() })
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()
        val prefs = this.activity!!.getSharedPreferences("IF prefs", MODE_PRIVATE)
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis)
        timerRunning = prefs.getBoolean("timerRunning", false)
        timelefttofast = prefs.getLong("timelefttofast",start)
        timeselected = prefs . getBoolean("timeselected",false)
        startTimeInMillis = prefs.getLong("selectedfast",startTimeInMillis)
        if (timerRunning) {
            mEndTime = prefs.getLong("mEndTime", 0)
            timeLeftInMillis = mEndTime - System.currentTimeMillis()
            if (timeLeftInMillis <= 0) {
                timeLeftInMillis = 0
                timerRunning = false
                updateCountDownText(timeLeftInMillis,"Your fast will finish at: \n")
                val intent = Intent(this.activity,SSaveFast::class.java)
                startActivity(intent)
            } else {
                startTimer()
            }
        }else if(timeselected){
            endtime = prefs.getLong("endtime",0)
            timelefttofast = endtime -System.currentTimeMillis()
            if (timelefttofast < 0) {
                timeLeftInMillis = (endtime + startTimeInMillis)- System.currentTimeMillis()
                startTimer()
                timeselected = false
                updateCountDownText(timeLeftInMillis,"You will start fasting after :\n")
            } else {
                timelefttofast()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            startTimeInMillis != 0L && !timeselected && !timerRunning -> {
                updateCountDownText(startTimeInMillis,"You selected:\n")
                whenbtn.text = "Set your fasting time start"
                nowbtn.visibility = View.VISIBLE
                Cancelbtn.visibility= View.VISIBLE
                prog.progress = 100
            }
            startTimeInMillis != 0L && timeselected && !timerRunning -> {
                whenbtn.visibility = View.INVISIBLE
                nowbtn.visibility = View.VISIBLE
                Cancelbtn.visibility = View.VISIBLE
            }
            startTimeInMillis != 0L && !timeselected && timerRunning -> {
                whenbtn.visibility = View.INVISIBLE
                nowbtn.visibility = View.INVISIBLE
                Cancelbtn.visibility = View.VISIBLE
            }

        }
    }

    override fun onStop() {
        super.onStop()
        val prefs = this.activity!!.getSharedPreferences("IF prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong("millisLeft", timeLeftInMillis)
        editor.putBoolean("timerRunning", timerRunning)
        editor.putLong("mEndTime", mEndTime)
        editor.putLong("timelefttofast",timelefttofast)
        editor.putBoolean("timeselected",timeselected)
        editor.putLong("endtime",endtime)
        editor.putLong("selectedfast",startTimeInMillis)
        editor.apply()
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }else if(countDownTimer2 != null){
            countDownTimer2!!.cancel()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun startTimer() {
        timeselected = false
        fastTracking(timeLeftInMillis)
        prog.visibility=View.VISIBLE
        prog.progress = i
        nowbtn.visibility=View.INVISIBLE
        if(countDownTimer2 != null){
            countDownTimer2!!.cancel()
        }

        mEndTime = System.currentTimeMillis() + timeLeftInMillis

        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                secondsRemaining = millisUntilFinished / 1000
                updateCountDownText(timeLeftInMillis,"Time left to eat :\n")
                prog.max=startTimeInMillis.toInt()
                val value = startTimeInMillis - timeLeftInMillis
                i = (value + 1000).toInt()
                prog.progress = i
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                timerRunning = false
                timer_count.text="FINISHED"
                i++
                prog!!.progress = startTimeInMillis.toInt()
                startTimeInMillis = 0L
                val intent = Intent (activity, SSaveFast::class.java)
                activity!!.startActivity(intent)

            }
        }.start()
        timerRunning = true
    }

    private fun timelefttofast(){
        endtime= System.currentTimeMillis() + timelefttofast
        beforeFastTracking(timelefttofast)
        countDownTimer2 = object : CountDownTimer(timelefttofast,1000){
            override fun onFinish() {
                if(startTimeInMillis != 0L){
                    timeLeftInMillis=startTimeInMillis
                    startTimer()
                    timeselected=false
                }else {
                    val fragreplace : FragmentTransaction = fragmentManager!!.beginTransaction()
                    fragreplace.replace(R.id.frag,Fragfast()).commit()
                    val botnav: BottomNavigationView = activity!!.findViewById(R.id.bott_nav)
                    botnav.selectedItemId= R.id.nav_fast
                    Toast.makeText( context,"Choose a fasting category !!!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onTick(millisUntilFinished: Long) {
                timelefttofast = millisUntilFinished
                updateCountDownText(timelefttofast,"You will start fasting after :\n")
            }
        }.start()
        timeselected= true
    }

    private fun cancel() {
        if(countDownTimer!= null){
            countDownTimer!!.cancel()
        }
        if(countDownTimer2!= null){
            countDownTimer2!!.cancel()
        }
    }

    private fun updateCountDownText(time: Long?, textsaid:String) {
        if(time==null){
            timer_count.text=textsaid
        }else {
            val hours = TimeUnit.MILLISECONDS.toHours(time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time))
            val seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
            val timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds)
            timer_count.text = textsaid + timeLeft
        }
    }

    private fun fastTracking(timecat: Long) {
        val message ="Time left to eat :"
        val message2 = "Congratulations!! you can start eating now !!"
        val serviceIntent = Intent(this.activity, TimeLeftToeat::class.java)
        serviceIntent.putExtra("inputExtra", timecat.toString())
        serviceIntent.putExtra("message", message)
        serviceIntent.putExtra("message2", message2)
        ContextCompat.startForegroundService(this.activity!!, serviceIntent)
    }
    private fun beforeFastTracking(timecat: Long) {
        val message ="You will start fasting in :"
        val message2 = "Your fast is started, Click the notification to get a time tracking notification for your fasting !!"
        val serviceIntent = Intent(this.activity, TimeLeftToFast::class.java)
        serviceIntent.putExtra("inputExtra", timecat.toString())
        serviceIntent.putExtra("message", message)
        serviceIntent.putExtra("message2", message2)
        ContextCompat.startForegroundService(this.activity!!, serviceIntent)
    }

    private fun stopService() {
        val vTimeLeftToFastService = Intent (this.activity, TimeLeftToFast::class.java)
        val vFastTrackService = Intent(this.activity, TimeLeftToeat::class.java)
        activity!!.stopService(vTimeLeftToFastService)
        activity!!.stopService(vFastTrackService)
    }
}