package com.therzarzayev.inochat.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.databinding.FragmentHomeBinding
import com.therzarzayev.inochat.models.PostModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var posts: ArrayList<PostModel>
    private val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        posts = arrayListOf()
        getPost()
        val layoutManager = LinearLayoutManager(context)
        binding.rvPosts.layoutManager = layoutManager
        binding.rvPosts.setHasFixedSize(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu_home, menu)
    }

    private fun getPost() {
        db.collection("posts").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            } else {
                if (snapshot != null && !snapshot.isEmpty) {
                    val documents = snapshot.documents
                    for (i in documents) {
                        val date = i.get("date") as Timestamp
                        val post = PostModel(
                            i.get("user").toString(), i.get("comment").toString(),
                            i.get("downloadUrl").toString(), date.toDate()
                        )
                        posts.add(post)
                    }
                }
            }
        }
    }
}