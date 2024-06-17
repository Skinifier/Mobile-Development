package com.capstone.skinifier.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.R
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(ProfileViewModel::class.java)

        val photo: ImageView = view.findViewById(R.id.photo)
        val username: TextView = view.findViewById(R.id.title)
        val skinType: TextView = view.findViewById(R.id.user_skin_type)
        val email: TextView = view.findViewById(R.id.user_email)
        val noHp: TextView = view.findViewById(R.id.user_nomor)

        profileViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            // Update UI elements with profile data
            // Example: Glide.with(this).load(profile.photo).into(photo)
            username.text = profile.username
            skinType.text = profile.skinType
            email.text = profile.email
            noHp.text = profile.noHp
        }

        profileViewModel.fetchProfile()

        return view
    }
}