package com.example.lab.domain.member

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberProperties: MemberProperties
) {

    lateinit var lastSignupEmail: String

    val configSummary: String by lazy {
        println("configSummary 계산 중")
        "default=${memberProperties.defaultStatus}"
    }

    @Transactional
    fun signup(memberSignupRequest: MemberSignupRequest): SignupResult {
        println("memberProperties : ${memberProperties}")

        try {
            println("lastSignupEmail (init 전) : ${lastSignupEmail}")
        } catch (e: UninitializedPropertyAccessException) {
            println("lateinit 예외 : ${e.message}")
        }

        lastSignupEmail = memberSignupRequest.email
        println("lastSignupEmail (init 후) : ${lastSignupEmail}")

        println("configSummary 1차 호출 : ${configSummary}")
        println("configSummary 2차 호출 : ${configSummary}")

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

    @Transactional(readOnly = true)
    fun getById(id: Long): MemberResponse {
        val member = memberRepository.getById(id)
        // 저장된 id는 null 일 수 없으니까 !!
        return MemberResponse(member.id!!, member.email, member.name)
    }
}