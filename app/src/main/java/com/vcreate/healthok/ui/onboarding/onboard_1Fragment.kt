package com.vcreate.healthok.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentOnboard1Binding


class onboard_1Fragment : Fragment() {

    private lateinit var binding: FragmentOnboard1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnboard1Binding.inflate(inflater, container, false)

        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewpager)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }
}