package com.example.sample.member;

/**
 * [Day 4] extension function 변환 과제
 *
 * 변환 포인트:
 * - static Util 클래스 → extension function
 * - MemberMapper.toResponse(member) → member.toResponse()
 *
 * Kotlin에서는 이렇게 씁니다:
 * fun Member.toResponse(): MemberResponse {
 *     return MemberResponse(id = id, email = email, name = name)
 * }
 */
public class MemberMapper {

    public static MemberResponse toResponse(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName()
        );
    }
}
