package com.example.intermittentfasting
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater

class HistoryViewAdapter(private val mListEx: ArrayList<FastEx>) :
    RecyclerView.Adapter<HistoryViewAdapter.ExampleViewHolder>() {

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vImageView: ImageView = itemView.findViewById(R.id.feelimg)
        var vTextView1: TextView = itemView.findViewById(R.id.fastcat)
        var vTextView2: TextView = itemView.findViewById(R.id.commenttxt)
        var vDate: TextView = itemView.findViewById(R.id.datetxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.example_fast, parent, false)
        return ExampleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = mListEx[position]
        holder.vImageView.setImageResource(currentItem.getImageResource())
        holder.vTextView1.text = currentItem.getText1()
        holder.vTextView2.text = currentItem.getText2()
        holder.vDate.text = currentItem.getdate()
    }

    override fun getItemCount(): Int {
        return mListEx.size
    }
}