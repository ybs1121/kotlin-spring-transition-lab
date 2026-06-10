package com.example.lab.domain.member

import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class MemberRepository(

) {
    private val store = mutableMapOf<Long, Member>()
    private val sequence = AtomicLong(1)

    fun save(member: Member): Member {
        val id: Long = sequence.getAndIncrement()

        val saved: Member = Member.reconstruct(id, member.email, member.name, member.password, member.memberStatus)
        store[id] = saved
        return saved
    }

    fun existsByEmail(email: String): Boolean {
        return store.values.any({
            it.email == email
        }
        )
    }

    fun getById(id: Long): Member {
        return store[id] ?: throw IllegalArgumentException()
    }
}