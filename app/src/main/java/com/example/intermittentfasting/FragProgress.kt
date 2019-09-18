package com.example.intermittentfasting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_progress.*
import androidx.recyclerview.widget.RecyclerView

class FragProgress : Fragment() {
    var Weightlist: ArrayList<ExampleWeight> = ArrayList()
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_progress,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        Weightlist.add(ExampleWeight("Ohayo","RFGDFG"))
        buildRecyclerView()

        adweight.setOnClickListener{
            openDialog()
        }
    }
    fun openDialog() {
        val exampleDialog = SaveWeight()
        exampleDialog.show(fragmentManager!!, "example dialog")
    }
    private fun buildRecyclerView(){
        rec.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this.activity!!)
        mAdapter = WeightAdapter(Weightlist)
        rec!!.layoutManager = mLayoutManager
        rec!!.adapter = mAdapter
    }
}