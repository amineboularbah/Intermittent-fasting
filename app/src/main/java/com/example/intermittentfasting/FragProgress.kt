package com.example.intermittentfasting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_progress.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.exweight.*
import kotlin.collections.ArrayList

class FragProgress : Fragment() {
    var weightList: ArrayList<ExampleWeight> = ArrayList()
    private var mAdapter: WeightAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_progress,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        weightList.reverse()
        buildRecyclerView()
        adweight.setOnClickListener{
            openDialog()
        }
    }
    fun openDialog() {
        val exampleDialog = SaveWeight()
        exampleDialog.show(fragmentManager!!, "Add weight dialog")
    }
    private fun loadData() {
        val sharedPreferences = this.activity!!.getSharedPreferences("weightHistory",Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("weightList", null)
        val type = object : TypeToken<ArrayList<ExampleWeight>>() {
        }.type
        if(json != null) {
            weightList = gson.fromJson(json, type)
        } }
    private fun buildRecyclerView(){
        rec.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this.activity!!)
        mAdapter = WeightAdapter(weightList)
        rec!!.layoutManager = mLayoutManager
        rec!!.adapter = mAdapter

        mAdapter!!.setOnItemClickListener(object : WeightAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                animation(Delete)
                deleteFast(position)
            }
        })
    }

    private fun saveData() {
        val sharedPreferences =this.activity!!.getSharedPreferences("weightHistory",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(weightList)
        editor.putString("weightList", json)
        editor.apply()
    }
    fun deleteFast(position: Int) {
        val deleteAlert = AlertDialog.Builder(this.activity)
        deleteAlert.setTitle("Confirmation")
        deleteAlert.setMessage("Are you sure ?")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                weightList.removeAt(position)
                mAdapter!!.notifyItemRemoved(position)
                saveData()
            }
            .setNegativeButton("Cancel"){ _: DialogInterface, _: Int->
                //Nothing will happen.
            }
        deleteAlert.show()
    }
    private fun animation(button: TableRow){
        val myAnim = AnimationUtils.loadAnimation(this.activity, R.anim.bouncef)
        val interpolator = Animation(0.2, 20.0)
        myAnim.interpolator = interpolator
        button.startAnimation(myAnim)
    }
}