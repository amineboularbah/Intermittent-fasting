package com.example.intermittentfasting
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bott_nav.setOnNavigationItemSelectedListener(navListener)
        supportFragmentManager.beginTransaction().replace(
            R.id.frag,
            Fragtimer()
        ).commit()
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_timer -> selectedFragment = Fragtimer()
            R.id.nav_fast -> selectedFragment = Fragfast()
            R.id.nav_history -> selectedFragment = FragHistory ()
            R.id.nav_weight -> selectedFragment = FragProgress ()
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.frag,
            selectedFragment!!
        ).commit()
        true
    }
    override fun onBackPressed() {

    }
}