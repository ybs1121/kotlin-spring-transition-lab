package com.example.sample.member;

/**
 * [Day 1] DTO 변환 과제
 *
 * 변환 포인트:
 * - id는 nullable (Long vs Long?)
 * - data class로 변환
 */
public class MemberResponse {

    private Long id;
    private String email;
    private String name;

    public MemberResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
