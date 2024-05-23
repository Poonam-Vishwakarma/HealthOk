package com.vcreate.healthok.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)


        val fragmentList = arrayListOf<Fragment>(
            onboard_1Fragment(),
            onboard_2Fragment(),
            onboard_3Fragment()
        )

        val adapter = OnBoardAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewpager.adapter = adapter



        return binding.root
    }

}