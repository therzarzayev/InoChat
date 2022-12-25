package com.therzarzayev.inochat.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.therzarzayev.inochat.R
import com.therzarzayev.inochat.adapter.PostAdapter
import com.therzarzayev.inochat.databinding.FragmentHomeBinding
import com.therzarzayev.inochat.models.PostModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var posts: ArrayList<PostModel>
    private lateinit var adapter: PostAdapter
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.materialToolbarChat.setOnClickListener{
            startActivity(Intent(activity,ChatActivity::class.java))
        }

        posts = arrayListOf()

        val layoutManager = LinearLayoutManager(context)
        binding.rvPosts.layoutManager = layoutManager
        binding.rvPosts.setHasFixedSize(true)
        getPost()
        adapter = PostAdapter(posts)
        binding.rvPosts.adapter = adapter
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPost() {
        db.collection("posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
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
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}