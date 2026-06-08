package com.example.lab.domain.member


fun Member.toResponse(): MemberResponse = MemberResponse(
    id?.let { it } ?: throw IllegalStateException("저장된 Member에 id가 없습니다"),
    email, name
).also { println("MemberResponse create : ${it}" ) }


