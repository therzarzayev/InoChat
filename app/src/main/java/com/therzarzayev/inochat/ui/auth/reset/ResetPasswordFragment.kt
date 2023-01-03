package com.therzarzayev.inochat.ui.auth.reset

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.therzarzayev.inochat.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.resetBtn.setOnClickListener {
            resetPassword()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resetPassword() {
        if (!TextUtils.isEmpty(binding.email.text)) {
            auth.sendPasswordResetEmail(binding.email.text.toString()).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.email.text = null
                    Toast.makeText(activity, "Zəhmət olmasa emailinizi yoxlayın", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(activity, "Xəta baş verdi", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            Toast.makeText(activity, "Zəhmət olmasa emailinizi daxil edin", Toast.LENGTH_LONG).show()
        }
    }
}