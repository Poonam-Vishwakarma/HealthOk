package com.vcreate.healthok.ui.onboarding


import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vcreate.healthok.R
import com.vcreate.healthok.databinding.FragmentSplashBinding
import com.vcreate.healthok.databinding.FragmentViewPagerBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler().postDelayed({
            if(onBoardingFinished()){
                findNavController().navigate(R.id.loginFragment)
            }else{
                findNavController().navigate(R.id.viewPagerFragment)
            }
        }, 3000)


        return binding.root

    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}