package com.therzarzayev.inochat.models

import java.util.*

data class PostModel(
    val user: String,
    val comment: String,
    val downloadUrl: String,
    val date: Date
)
