package com.example.lab.domain.post

enum class PostStatus(
    val description: String
) {
    PUBLISHED("게시"),
    DELETED("삭제")
}