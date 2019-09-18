package com.example.intermittentfasting
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
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList


class FragHistory : Fragment() {
    private var mAdapter: HistoryViewAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var fastlist:ArrayList<FastEx> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_historyr,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            loadData()
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
    }
}