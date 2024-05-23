package com.example.healthok.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthok.data.model.Categories
import com.vcreate.healthok.R

class CategoriesAdapter(
    private val data : List<Categories>
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = data[position]
        holder.name.setText(currentCategory.categoryName)
        holder.name.gravity = Gravity.CENTER
        holder.image.setImageResource(currentCategory.imageUrl)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.item_categories_product_name)
        val image : ImageView = itemView.findViewById(R.id.item_categories_image)
    }
}