package com.vcreate.healthok.ui.main.user.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vcreate.healthok.adapter.ArticleAdapter
import com.vcreate.healthok.adapter.DoctorAdapter
import com.vcreate.healthok.R
import com.vcreate.healthok.data.remote.FirebaseClient
import com.vcreate.healthok.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), DoctorAdapter.OnDoctorItemClick, ArticleAdapter.OnArticleItemClick{

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        onClickListener()

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRecyclerView.layoutManager = layoutManager

        FirebaseClient.getAllTheDoctors { doctorList ->
            val doctorAdapter = DoctorAdapter(data = doctorList, R.layout.item__doctor_view_small_circle, this)
            binding.fragmentHomeRecyclerView.adapter = doctorAdapter
        }

        val articlelayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeArticleRecyclerView.layoutManager = articlelayoutManager

        FirebaseClient.getAllTheArticles {articleList ->
            val articleAdapter = ArticleAdapter(data = articleList, this)
            binding.fragmentHomeArticleRecyclerView.adapter = articleAdapter
        }

        return binding.root

    }

    private fun onClickListener() {
        binding.doctor.setOnClickListener {
            findNavController().navigate(R.id.medicationReminderFragment)
        }

        binding.cycle.setOnClickListener {
            findNavController().navigate(R.id.cycleFragment)
        }

        binding.hospital.setOnClickListener {
            findNavController().navigate(R.id.hospitalLocationFragment)
        }

        binding.water.setOnClickListener {
            showImageDialog()
        }
    }

    private fun showImageDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.water_dialog, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.water)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    override fun onDoctorClick(position: Int, uid: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDoctorDetailFragment2(uid)
        findNavController().navigate(action)
    }

    override fun onArticleClick(position: Int, uid: Long) {
        val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(uid)
        findNavController().navigate(action)
    }
}