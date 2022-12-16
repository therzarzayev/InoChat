package com.therzarzayev.inochat.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.ActivityAuthBinding
import com.therzarzayev.inochat.ui.main.HomeFragment
import com.therzarzayev.inochat.ui.main.LikeFragment
import com.therzarzayev.inochat.ui.main.ProfileFragment
import com.therzarzayev.inochat.ui.main.SearchFragment

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(LoginFragment())
    }
    private fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout_auth, fragment)
        transaction.commit()
    }
}