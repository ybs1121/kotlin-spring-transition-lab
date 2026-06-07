package com.example.lab.domain.member

sealed interface SignupResult {
    data object Success : SignupResult
    data class Fail(val message: String) : SignupResult
}