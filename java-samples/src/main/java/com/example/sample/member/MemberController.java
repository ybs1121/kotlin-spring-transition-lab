package com.example.sample.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * [Week 2] Controller 변환 과제
 *
 * 변환 포인트:
 * - @Valid + @RequestBody → Kotlin data class에서 @field: 필요
 * - ResponseEntity<T> 그대로 사용 가능
 * - @PathVariable Long id → nullable 여부 고민 불필요 (non-null)
 * - 생성자 주입 방식 동일
 *
 * 함정 포인트:
 * - Kotlin data class + @NotBlank 붙이면 컴파일은 되지만 검증이 안 될 수 있음
 *   → @field:NotBlank 로 use-site target 지정 필요
 * - Jackson이 Kotlin data class 역직렬화 못할 수 있음
 *   → jackson-module-kotlin 의존성 필요
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<SignupResult> signup(@Valid @RequestBody MemberSignupRequest request) {
        SignupResult result = memberService.signup(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }
}
