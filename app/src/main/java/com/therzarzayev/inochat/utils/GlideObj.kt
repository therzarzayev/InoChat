package com.therzarzayev.inochat.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.therzarzayev.inochat.R

object GlideObj {
    fun getPostImg(context: Context, url: String, image: ImageView){
        Glide
            .with(context)
            .load(url)
            .placeholder(R.drawable.defult)
            .into(image)
    }
}