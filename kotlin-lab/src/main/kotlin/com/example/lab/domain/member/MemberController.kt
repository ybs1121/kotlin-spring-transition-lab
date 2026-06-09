package com.example.lab.domain.member

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


// 디폴트로 생성자 val 인 생성자 만들어줘서 생성자 어노테이션이나 생성자 안만들어도 되나?

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping
    fun signup(@Valid @RequestBody memberSignupRequest: MemberSignupRequest): ResponseEntity<SignupResult> {
        println("member : ${memberSignupRequest}")
        return ResponseEntity.ok(memberService.signup(memberSignupRequest))
    }

}