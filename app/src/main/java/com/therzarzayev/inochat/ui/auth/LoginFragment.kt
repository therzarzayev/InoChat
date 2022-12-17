package com.therzarzayev.inochat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.FragmentLoginBinding
import com.therzarzayev.inochat.ui.main.MainActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.buttonLogin.setOnClickListener {
            login()
        }
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

    private fun login() {
        val email = binding.email.text.toString().trim()
        val password = binding.passwd.text.toString().trim()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Email or password is incorrect", Toast.LENGTH_SHORT).show()
        }
    }
}