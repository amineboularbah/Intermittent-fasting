package com.example.intermittentfasting
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeightAdapter(private val weightlist: ArrayList<ExampleWeight>) :
    RecyclerView.Adapter<WeightAdapter.ExViewHolder>()  {
    override fun onBindViewHolder(holder: ExViewHolder, position: Int) {
        val currentItem = weightlist[position]
        holder.vTextView1.text = currentItem.getText1()
        holder.vTextView2.text = currentItem.getText2()
    }

    class ExViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vTextView1: TextView = itemView.findViewById(R.id.datxt)
        var vTextView2: TextView = itemView.findViewById(R.id.weighttxt)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ExViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.exweight, parent, false)
        return ExViewHolder(v)
    }

    override fun getItemCount(): Int {
        return weightlist.size
    }

}