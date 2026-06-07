package com.example.sample.member;

/**
 * [Day 3] 도메인 클래스 변환 과제
 *
 * 변환 포인트:
 * - private 생성자 → private constructor
 * - static factory method → companion object
 * - if + throw → require / check
 * - 변경 가능한 필드 → var, 불변 필드 → val
 */
public class Member {

    private Long id;
    private String email;
    private String name;
    private String password;
    private MemberStatus status;

    private Member(
            Long id,
            String email,
            String name,
            String password,
            MemberStatus status
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public static Member signup(String email, String name, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }
        return new Member(null, email, name, password, MemberStatus.ACTIVE);
    }



    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        this.name = name;
    }

    public void withdraw() {
        if (this.status == MemberStatus.WITHDRAWN) {
            throw new IllegalStateException("이미 탈퇴한 회원입니다.");
        }
        this.status = MemberStatus.WITHDRAWN;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public MemberStatus getStatus() { return status; }
}
