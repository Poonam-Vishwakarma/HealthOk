package com.vcreate.healthok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vcreate.healthok.models.Article
import com.vcreate.healthok.R

class ArticleAdapter(
    private val data : List<Article>,
    private val listener: OnArticleItemClick
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    interface OnArticleItemClick {
        fun onArticleClick(position: Int, uid: Long)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article_small, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentArticle: Article = data[position]
        holder.name.text = currentArticle.articleName
        holder.author.text = currentArticle.authorName
        holder.date.text = currentArticle.articleDate

        holder.itemView.setOnClickListener {
            listener.onArticleClick(position, currentArticle.articleId)
        }

        Picasso.get()
            .load(currentArticle.articleImage)
            .placeholder(R.drawable.article_fruits_image)
            .into(holder.imageView)
    }


    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_article_article_image)
        val name: TextView = itemView.findViewById(R.id.item_article_article_name)
        val author: TextView = itemView.findViewById(R.id.item_article_article_author_name)
        val date : TextView = itemView.findViewById(R.id.item_article_date)
        val bookmark: ImageView = itemView.findViewById(R.id.item_article_article_bookmark)
    }
}