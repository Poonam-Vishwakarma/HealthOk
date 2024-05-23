package com.vcreate.healthok.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vcreate.healthok.models.Doctor
import com.vcreate.healthok.R

class DoctorAdapter (
    private val data: List<Doctor>,
    private val layout: Int,
    private val listener: OnDoctorItemClick) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        return ViewHolder(view)
    }

    interface OnDoctorItemClick {
        fun onDoctorClick(position: Int, uid: String)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor = data[position]
        holder.name.text = currentDoctor.doctorName
        holder.speciality.text = currentDoctor.speciality
        holder.rating.text = currentDoctor.rating.toString()
        holder.distance.text = currentDoctor.address.toString()
        if (currentDoctor.profilePhoto.isNotEmpty()) {
            Picasso.get()
                .load(currentDoctor.profilePhoto)
                .placeholder(R.drawable.account_profile)
                .into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.account_profile)
        }

        holder.itemView.setOnClickListener {
            listener.onDoctorClick(position, currentDoctor.doctorId)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.layout_doctor_image)
        val name: TextView = itemView.findViewById(R.id.layout_doctor_name)
        val speciality: TextView = itemView.findViewById(R.id.layout_doctor_speciality)
        val rating: TextView = itemView.findViewById(R.id.layout_doctor_rating)
        val distance: TextView = itemView.findViewById(R.id.layout_doctor_distance)
    }
}