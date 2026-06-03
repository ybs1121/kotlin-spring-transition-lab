package com.example.sample.member;

/**
 * [Day 1] enum 변환 과제
 *
 * 변환 포인트:
 * - Java enum → Kotlin enum class
 * - 생성자 문법 차이
 * - getter → 프로퍼티 직접 접근
 */
public enum MemberStatus {

    ACTIVE("활성"),
    WITHDRAWN("탈퇴");

    private final String description;

    MemberStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
