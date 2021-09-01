package com.baloot_mahdijamshidi.ui.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.baloot_mahdijamshidi.classes.BottomSheetDialog
import com.baloot_mahdijamshidi.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding =
            FragmentProfileBinding.inflate(
                inflater, container, false
            )


        binding?.linkedin?.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://mahdi-jamshidi-041957184"))
            val packageManager = activity?.packageManager
            val list =
                packageManager?.queryIntentActivities(intent!!, PackageManager.MATCH_DEFAULT_ONLY)
            if (list?.isEmpty()!!) {
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/mahdi-jamshidi-041957184/")
                )
            }
            startActivity(intent)
        }

        binding?.about?.setOnClickListener {
            val fragManager: FragmentManager =
                activity?.supportFragmentManager!!
            val fragment = BottomSheetDialog.newInstance()
            fragment.show(fragManager, "me")
        }

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}