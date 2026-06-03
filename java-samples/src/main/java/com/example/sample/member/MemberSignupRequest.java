package com.example.sample.member;

/**
 * [Day 1] DTO 변환 과제
 *
 * 변환 포인트:
 * - class → data class
 * - 생성자 + getter → primary constructor + val
 * - nullable 여부 고민 (String vs String?)
 */
public class MemberSignupRequest {

    private String email;
    private String name;
    private String password;

    public MemberSignupRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
