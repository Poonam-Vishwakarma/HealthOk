package com.vcreate.healthok.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentOnboard2Binding
import com.vcreate.healthok.databinding.FragmentOnboard3Binding


class onboard_3Fragment : Fragment() {

    private lateinit var binding: FragmentOnboard3Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboard3Binding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
            onBoardingFinished()
        }

        return binding.root
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }


}