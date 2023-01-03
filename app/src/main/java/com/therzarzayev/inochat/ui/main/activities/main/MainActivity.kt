package com.therzarzayev.inochat.ui.main.activities.main

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.ActivityMainBinding
import com.therzarzayev.inochat.ui.main.activities.share.ShareActivity
import com.therzarzayev.inochat.ui.main.fragments.home.HomeFragment
import com.therzarzayev.inochat.ui.main.fragments.like.LikeFragment
import com.therzarzayev.inochat.ui.main.fragments.profile.ProfileFragment
import com.therzarzayev.inochat.ui.main.fragments.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavMenu.background = null
        replaceFragment(HomeFragment())
        binding.bottomNavMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.add -> addPost()
                R.id.like -> {
                    replaceFragment(LikeFragment())

                }
                R.id.person -> replaceFragment(ProfileFragment())
                else -> Log.INFO
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    private fun addPost() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ), 1
            )
        } else {
            startActivity(Intent(applicationContext, ShareActivity::class.java))
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(Intent(applicationContext, ShareActivity::class.java))
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}