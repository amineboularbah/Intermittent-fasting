package com.example.intermittentfasting
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.TableRow


class HistoryViewAdapter(private val mFastList: ArrayList<FastEx>) :
    RecyclerView.Adapter<HistoryViewAdapter.ExampleViewHolder>() {
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ExampleViewHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
         var vImageView: ImageView = itemView.findViewById(R.id.feelimg)
         var vTextView1: TextView = itemView.findViewById(R.id.fastcat)
         var vTextView2: TextView = itemView.findViewById(R.id.commenttxt)
         var vDate: TextView = itemView.findViewById(R.id.datetxt)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.example_fast, parent, false)
        return ExampleViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = mFastList[position]

        holder.vImageView.setImageResource(currentItem.getImageResource())
        holder.vTextView1.text = currentItem.getText1()
        holder.vTextView2.text = currentItem.getText2()
        holder.vDate.text = currentItem.getdate()
    }

    override fun getItemCount(): Int {
        return mFastList.size
    }
}