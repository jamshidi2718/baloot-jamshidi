package com.baloot_mahdijamshidi.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.baloot_mahdijamshidi.R
import com.baloot_mahdijamshidi.databinding.FragmentHomeBinding
import com.baloot_mahdijamshidi.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {


    private var binding: FragmentInfoBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding =
            FragmentInfoBinding.inflate(
                inflater, container, false
            )

        binding?.titel?.text = InfoFragmentArgs.fromBundle(requireArguments()).title
        binding?.content?.text = InfoFragmentArgs.fromBundle(requireArguments()).content

        return binding!!.root
    }
}