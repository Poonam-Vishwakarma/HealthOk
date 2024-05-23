package com.vcreate.healthok.ui.main.user.home.medicationreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vcreate.healthok.R
import com.vcreate.healthok.models.Reminder

class ReminderAdapter (
    private var data: MutableList<Reminder>,
    private val listener: OnItemClick
) : RecyclerView.Adapter <ReminderAdapter.ViewHolder>() {

    interface OnItemClick {
        fun onClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: MutableList<Reminder>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currReminder = data[position]
        if (currReminder.message.isNotEmpty()) {
            holder.name.text = currReminder.message
        }

        if (currReminder.date.isNotEmpty()) {
            holder.date.text = currReminder.date
        }

        holder.imageView.setOnClickListener {
            // Pass the position to the click listener
            listener.onClick(position)
        }

    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.item_reminder_delete)
        val name: TextView = itemView.findViewById<TextView>(R.id.item_reminder_name)
        val date: TextView = itemView.findViewById<TextView>(R.id.item_reminder_date)
    }
}