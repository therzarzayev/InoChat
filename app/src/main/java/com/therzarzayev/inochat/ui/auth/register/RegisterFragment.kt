package com.therzarzayev.inochat.ui.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.therzarzayev.inochat.databinding.FragmentRegisterBinding
import com.therzarzayev.inochat.ui.main.activities.main.MainActivity

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.buttonSignup.setOnClickListener {
            hideKeyboard()
            register()
        }

        binding.hidePassword.setOnClickListener {
            binding.passwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.showPassword.visibility = View.VISIBLE
            binding.hidePassword.visibility = View.GONE
        }

        binding.showPassword.setOnClickListener {
            binding.passwd.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.hidePassword.visibility = View.VISIBLE
            binding.showPassword.visibility = View.GONE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun register() {
        val email = binding.email.text.toString().trim()
        val password = binding.passwd.text.toString().trim()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.signupLay.visibility = View.GONE
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

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}