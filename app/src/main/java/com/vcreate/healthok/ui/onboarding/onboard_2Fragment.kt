package com.vcreate.healthok.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentOnboard2Binding


class onboard_2Fragment : Fragment() {

    private lateinit var binding: FragmentOnboard2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnboard2Binding.inflate(inflater, container, false)

        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewpager)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return binding.root
    }
}
