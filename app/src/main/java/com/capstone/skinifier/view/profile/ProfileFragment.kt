package com.capstone.skinifier.view.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.capstone.skinifier.R
import com.capstone.skinifier.view.login.LoginActivity
import com.capstone.skinifier.view.soldProduct.SoldProductActivity
import com.capstone.skinifier.view.viewModelFactory.ViewModelFactory
import com.capstone.skinifier.view.wishlist.WishlistActivity

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

        profileViewModel.navigateToLogin.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                navigateToLogin()
                profileViewModel.navigationComplete()
            }
        }

        profileViewModel.fetchProfile()

        return view
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        view?.findViewById<Button>(R.id.logoutButton)?.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        view?.findViewById<CardView>(R.id.cardWishtlist)?.setOnClickListener {
            val intent = Intent(requireContext(), WishlistActivity::class.java)
            startActivity(intent)
        }
        view?.findViewById<CardView>(R.id.cardSoldProduct)?.setOnClickListener {
            val intent = Intent(requireContext(), SoldProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLogoutConfirmationDialog() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.logout_confirmation))
                .setMessage(getString(R.string.are_you_sure_you_want_to_logout))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    profileViewModel.logout()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}