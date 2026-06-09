package com.example.sample.member;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * [Week 2] Repository 변환 과제
 *
 * 변환 포인트:
 * - @Repository → @Repository (동일, kotlin-spring allopen 필요)
 * - Optional → nullable 타입으로 교체 가능
 * - AtomicLong id 채번 방식 유지 or 다른 방식 고민
 */
@Repository
public class MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Member save(Member member) {
        Long id = sequence.getAndIncrement();
        // 실제 JPA였다면 영속화 후 id가 채번되지만, 여기선 직접 세팅
        Member saved = Member.signup(member.getEmail(), member.getName(), member.getPassword());
        store.put(id, saved);
        return saved;
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(m -> m.getEmail().equals(email));
    }
}
