package com.example.intermittentfasting
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeightAdapter(private val weightlist: ArrayList<ExampleWeight>) :
    RecyclerView.Adapter<WeightAdapter.ExViewHolder>()  {
    private var vListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        vListener = listener
    }
    class ExViewHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        var vTextView1: TextView = itemView.findViewById(R.id.savedWeight)
        var vTextView2: TextView = itemView.findViewById(R.id.weightdate)
        private var vDeleteImage: TableRow = itemView.findViewById(R.id.Delete)
        init {
            vDeleteImage.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ExViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.exweight, parent, false)
        return ExViewHolder(v, vListener)
    }
    override fun onBindViewHolder(holder: ExViewHolder, position: Int) {
        val currentItem = weightlist[position]
        holder.vTextView1.text = currentItem.getText1()
        holder.vTextView2.text = currentItem.getText2()
    }


    override fun getItemCount(): Int {
        return weightlist.size
    }

}