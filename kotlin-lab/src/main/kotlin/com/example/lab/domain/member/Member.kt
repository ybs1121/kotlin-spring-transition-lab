package com.example.lab.domain.member

class Member private constructor(
    val id: Long?,
    val email: String,
    name: String,
    val password: String,
    memberStatus: MemberStatus
) {
    var name: String = name
        private set
    var memberStatus: MemberStatus = memberStatus
        private set


    companion object {
        fun signup(email: String, name: String, password: String): Member {
            if (email.isBlank()) {
                throw IllegalArgumentException("이메일은 필수입니다.")
            }
            // require(email.isNotBlank()) { "이메일은 필수입니다." }
            if (name.isBlank()) {
                throw IllegalArgumentException("이름은 필수입니다.")
            }
            // require(name.isNotBlank()) { "이름은 필수입니다." }
            if (password.length < 8) {
                throw IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.")
            }
            // require(password.length >= 8) { "비밀번호는 8자 이상이어야 합니다." }

            return Member(null, email, name, password, MemberStatus.ACTIVE).also {
                println("Member create : ${it}")
            }
        }
    }


    fun changeName(name: String) {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 필수입니다.")
        }
        // require(name.isNotBlank()) { "이름은 필수입니다." }
        this.name = name
    }

    fun withdraw() {
        if (this.memberStatus == MemberStatus.WITHDRAWN) {
            throw IllegalStateException("이미 탈퇴한 회원입니다.")
        }
        // check(memberStatus != MemberStatus.WITHDRAWN) { "이미 탈퇴한 회원입니다." }

        this.memberStatus = MemberStatus.WITHDRAWN
    }

//    fun getId(): Long? {
//        return this.id;
//    }
//
//    fun getEmail(): String {
//        return this.email
//    }
//
//    fun getName(): String {
//        return this.name
//    }
//
//    fun getPassword(): String {
//        return this.password
//    }
//
//    fun getMemberStatus(): MemberStatus {
//        return this.memberStatus
//    }


}