package com.example.lab.domain.post

data class PostResponse(
    val id: Long,
    val memberId: Long,
    val title: String,
    val content: String,
    val status: PostStatus
)
