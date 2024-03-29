package com.therzarzayev.inochat.ui.auth.login


import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.FragmentLoginBinding
import com.therzarzayev.inochat.ui.auth.register.RegisterFragment
import com.therzarzayev.inochat.ui.auth.reset.ResetPasswordFragment
import com.therzarzayev.inochat.ui.main.activities.main.MainActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        binding.buttonLogin.setOnClickListener {
            hideKeyboard()
            login()
        }
        binding.buttonSignup.setOnClickListener {
            replaceFragment(RegisterFragment())
        }
        binding.titleForgetPasswd.setOnClickListener{
            replaceFragment(ResetPasswordFragment())
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
            binding.loginLay.visibility = View.GONE
            binding.progressRel.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}