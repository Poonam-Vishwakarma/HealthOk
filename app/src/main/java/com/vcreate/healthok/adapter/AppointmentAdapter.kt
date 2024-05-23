package com.vcreate.healthok.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.models.Appointment
import timber.log.Timber

class AppointmentAdapter (
    private val context: Context,
    private val data: List<Appointment>,
    private val onAppointmentItemClick: AppointmentAdapter.OnAppointmentItemClick) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(){

    interface OnAppointmentItemClick {
        fun onClick(position: Int, uid: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.doctorImage)
        val name: TextView = itemView.findViewById(R.id.textViewDoctorName)
        val date: TextView = itemView.findViewById(R.id.textViewDate)
        val time: TextView = itemView.findViewById(R.id.textViewTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentAppointment = data[position]

        FirebaseClient.getDoctorDataByUid(currentAppointment.doctorId) { doctor ->

            if (doctor != null) {
                // Doctor data retrieved successfully
                holder.name.text = doctor.doctorName
                holder.date.text = currentAppointment.appointmentData
                holder.time.text = currentAppointment.appointmentTime

                if (doctor.profilePhoto.isNotEmpty()) {
                    Picasso.get()
                        .load(doctor.profilePhoto)
                        .placeholder(R.drawable.account_profile)
                        .into(holder.imageView)
                } else {
                    holder.imageView.setImageResource(R.drawable.account_profile)
                }
            } else {
                Timber.tag("error").d("Doctor Data not Found")
                Toast.makeText(context, "Doctor Data not Found", Toast.LENGTH_SHORT).show()
            }
        }

        holder.itemView.setOnClickListener {
            onAppointmentItemClick.onClick(position, currentAppointment.appointmentId)
        }
    }

}