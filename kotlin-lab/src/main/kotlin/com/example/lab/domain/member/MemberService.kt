package com.example.lab.domain.member

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun signup(memberSignupRequest: MemberSignupRequest): SignupResult {
        if (memberRepository.existsByEmail(memberSignupRequest.email)) {
            throw IllegalArgumentException("이미 사용 중인 이메일입니다:  ${memberSignupRequest.email}")
        }
        val member: Member = Member.signup(
            memberSignupRequest.email,
            memberSignupRequest.name,
            memberSignupRequest.password
        )

        memberRepository.save(member)
        return SignupResult.Success

    }
}