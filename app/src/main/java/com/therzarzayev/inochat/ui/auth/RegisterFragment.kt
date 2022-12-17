package com.therzarzayev.inochat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.therzarzayev.inochat.databinding.FragmentRegisterBinding
import com.therzarzayev.inochat.ui.main.MainActivity

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        binding.checkRememberMe.setOnCheckedChangeListener { _, isChecked ->
            binding.buttonSignup.isEnabled = isChecked
        }

        binding.buttonSignup.setOnClickListener {
            register()
        }

        return binding.root
    }

    private fun register() {
        val email = binding.email.text.toString().trim()
        val password = binding.passwd.text.toString().trim()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.progressRel.visibility = View.VISIBLE
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Email or password is empty", Toast.LENGTH_SHORT).show()
        }
    }
}