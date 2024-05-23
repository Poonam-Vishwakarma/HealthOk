package com.vcreate.healthok.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vcreate.healthok.ui.authentication.forgetpassword.ForgetPasswordUsingEmailFragment
import com.vcreate.healthok.ui.authentication.forgetpassword.ForgetPasswordUsingPhoneFragment

class ForgetPasswordTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return ForgetPasswordUsingEmailFragment()
        } else {
            return ForgetPasswordUsingPhoneFragment()
        }
    }
}