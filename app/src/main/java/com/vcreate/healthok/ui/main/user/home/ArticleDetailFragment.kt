package com.vcreate.healthok.ui.main.user.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentArticleDetailBinding
import com.vcreate.healthok.models.Article
import com.vcreate.healthok.ui.main.user.schedule.DoctorDetailFragmentArgs


class ArticleDetailFragment : Fragment() {

    private lateinit var binding: FragmentArticleDetailBinding
    private val args: ArticleDetailFragmentArgs by navArgs()
    private var uid: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        uid = args.ARTICLEID
        loadData()

        return binding.root
    }

    private fun loadData() {
        FirebaseClient.getArticleDataByUId(uid) { articleData ->
            if (articleData != null) {
                binding.name.text = articleData.articleName
                binding.about.text = articleData.articleContent
                binding.location.text = articleData.articleDate
                binding.specialist.text = articleData.authorName

                if (articleData.articleImage.isNotEmpty()) {
                    Picasso.get()
                        .load(articleData.articleImage)
                        .placeholder(R.drawable.account_profile)
                        .into(binding.imageView)
                } else {
                    binding.imageView.setImageResource(R.drawable.account_profile)
                }
            } else {
                Log.d("myTag", "User data not found")
            }
        }
    }

}