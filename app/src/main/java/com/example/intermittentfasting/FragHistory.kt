package com.example.intermittentfasting
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.frag_historyr.*
import com.google.gson.Gson
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.view.animation.AnimationUtils
import android.widget.TableRow
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.example_fast.*
import kotlin.collections.ArrayList

class FragHistory : Fragment() {
    private var mAdapter: HistoryViewAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var fastlist:ArrayList<FastEx> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_historyr,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            loadData()
            fastlist.reverse()
            buildRecyclerView()

    }

    private fun loadData() {
        val sharedPreferences = this.activity!!.getSharedPreferences("fasthistory", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("fastlist", null)
        val type = object : TypeToken<ArrayList<FastEx>>() {
        }.type
        if(json != null) {
            fastlist = gson.fromJson(json, type)
        }
    }

    private fun buildRecyclerView(){
        recycl.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this.activity!!)
        mAdapter = HistoryViewAdapter(fastlist)
        recycl!!.layoutManager = mLayoutManager
        recycl!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener(object : HistoryViewAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                animation(DeleteFast)
                deleteFast(position)
            }
        })
    }
    private fun saveData() {
        val sharedPreferences =this.activity!!.getSharedPreferences("fasthistory", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(fastlist)
        editor.putString("fastlist", json)
        editor.apply()
    }

    fun deleteFast(position: Int) {
        val deleteAlert = AlertDialog.Builder(this.activity)
        deleteAlert.setTitle("Confirmation")
        deleteAlert.setMessage("Do you want to delete this fast ?")
        .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            fastlist.removeAt(position)
            mAdapter!!.notifyItemRemoved(position)
            saveData()
        }
            .setNegativeButton("Cancel"){_:DialogInterface,_: Int->
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