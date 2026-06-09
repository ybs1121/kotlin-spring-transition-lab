package com.example.sample.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [Week 2] Service 변환 과제
 *
 * 변환 포인트:
 * - @Service → @Service (kotlin-spring allopen 없으면 @Transactional 프록시 생성 실패)
 * - @Transactional 클래스/메서드 레벨 선택
 * - Optional.orElseThrow() → ?: throw 로 교체 가능
 * - 중복 체크 패턴: if + throw → require / check
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public SignupResult signup(MemberSignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + request.getEmail());
        }

        Member member = Member.signup(
                request.getEmail(),
                request.getName(),
                request.getPassword()
        );

        Member saved = memberRepository.save(member);
        return SignupResult.success(saved.getEmail());
    }

    public MemberResponse getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + id));

        return new MemberResponse(id, member.getEmail(), member.getName());
    }
}
