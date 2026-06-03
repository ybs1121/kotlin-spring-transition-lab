package com.example.lab.domain.member

data class MemberSignupRequest(
    val email: String,
    val name: String,
    val password: String,
)
