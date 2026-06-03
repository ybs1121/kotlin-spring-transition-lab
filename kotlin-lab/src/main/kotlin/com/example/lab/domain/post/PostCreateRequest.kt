package com.example.lab.domain.post

data class PostCreateRequest(
    val memberId: Long,
    val title: String,
    val content: String
)
