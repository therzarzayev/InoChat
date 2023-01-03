package com.therzarzayev.inochat.ui.main.activities.share

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.therzarzayev.inochat.databinding.ActivityShareBinding
import java.util.*


class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding
    private var selectedImageUri: Uri? = null
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView2.setOnClickListener {
            chooseImg()
        }
        binding.shareBtn.setOnClickListener {
            sharePost()
            //getPost()
        }
    }

    private fun sharePost() {
        if (selectedImageUri != null) {
            val uuid = UUID.randomUUID()
            val imgName = "$uuid.jpg"
            val storage = Firebase.storage
            val storageRef = storage.reference
            val imgRef = storageRef.child("images").child(imgName)
            imgRef.putFile(selectedImageUri!!).addOnSuccessListener {
                val uploadedImageRef = storageRef.child("images").child(imgName)
                uploadedImageRef.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()
                    val postMap = hashMapOf<String, Any>()
                    postMap["downloadUrl"] = downloadUrl
                    postMap["user"] = auth.currentUser!!.email.toString()
                    postMap["comment"] = binding.editTextTextMultiLine.text.toString()
                    postMap["date"] = Timestamp.now()

                    db.collection("posts").add(postMap).addOnSuccessListener {
                            Toast.makeText(applicationContext, "Posted", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        }
                }

            }
        }
    }

    private fun chooseImg() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        launcher.launch(i)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null && data.data != null) {
                    selectedImageUri = data.data!!
                    var selectedImageBitmap: Bitmap? = null
                    try {
                        selectedImageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                    contentResolver, selectedImageUri!!
                                )
                            )
                        } else {
                            MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    binding.imageView2.setImageBitmap(selectedImageBitmap)
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImg()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}