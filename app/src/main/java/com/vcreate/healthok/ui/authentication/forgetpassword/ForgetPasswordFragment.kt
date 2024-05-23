package com.vcreate.healthok.ui.authentication.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.vcreate.healthok.R
import com.vcreate.healthok.adapter.ForgetPasswordTabAdapter
import com.vcreate.healthok.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var forgetPasswordTabAdapter: ForgetPasswordTabAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        forgetPasswordTabAdapter = ForgetPasswordTabAdapter(requireActivity().supportFragmentManager, lifecycle)

        tabSetup()

        return binding.root
    }

    private fun tabSetup() {
        val tabLayout: TabLayout = binding.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("Email"))
        tabLayout.addTab(tabLayout.newTab().setText("Phone"))

        binding.fpsViewpager.adapter = forgetPasswordTabAdapter
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.fpsViewpager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.fpsViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}