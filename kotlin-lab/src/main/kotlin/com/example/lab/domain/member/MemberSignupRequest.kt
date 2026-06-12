package com.example.lab.domain.member

import jakarta.validation.constraints.NotBlank

data class MemberSignupRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val password: String,

    val nickname: String?
)
