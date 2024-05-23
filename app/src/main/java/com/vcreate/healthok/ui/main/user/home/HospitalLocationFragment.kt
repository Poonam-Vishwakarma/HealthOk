package com.vcreate.healthok.ui.main.user.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.vcreate.healthok.databinding.FragmentHospitalLocationBinding


class HospitalLocationFragment : Fragment() {

    private lateinit var binding: FragmentHospitalLocationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHospitalLocationBinding.inflate(layoutInflater)
        binding.b1.setOnClickListener {
            val source: String = binding.e1.text.toString()
            val destination: String = binding.e2.text.toString()
            if (source == "" && destination == "") {
                Toast.makeText(
                    requireContext(),
                    "Enter both source and destination",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val uri = Uri.parse("https://www.google.com/maps/dir/$source/$destination")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }

        return binding.root
    }


}