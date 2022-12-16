package com.therzarzayev.inochat.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonSignup.setOnClickListener {
            replaceFragment(RegisterFragment())
        }


        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout_auth, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}