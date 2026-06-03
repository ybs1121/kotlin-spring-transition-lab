# kotlin-spring-transition-lab

## 프로젝트 목적
Java/Spring 4년차 개발자가 Kotlin/Spring으로 전환하기 위한 학습 프로젝트.
코드 결과물보다 학습 과정이 중요함.]



## Claude 행동 규칙

### 절대 하지 말 것
- 요청하지 않은 Kotlin 코드를 먼저 작성하지 말 것
- "이렇게 짜드릴까요?" 식의 제안 금지
- 한 번에 여러 개념을 섞어서 설명하지 말 것

### 항상 할 것
- 내가 먼저 Kotlin으로 짜본 뒤 리뷰 요청하면 그때 피드백
- 에러가 났을 때 정답을 바로 주지 말고, 어디를 봐야 하는지 힌트부터
- 내 코드의 "동작은 하지만 Kotlin스럽지 않은" 지점을 짚어줄 것
- 리뷰 시 Good / Improve / Kotlin-specific 세 항목으로 구분해서 피드백

### 현재 주차
1주차: Kotlin 핵심 문법 변환 실습

## 학습 우선순위
- 필수: val/var, nullable + ?. + ?:, data class, require/check
- 핵심: sealed interface, extension function, scope function (let/also/apply)
- 보조: collection functions, companion object

## 변환 과제 순서
- Day 1: DTO 변환 (MemberSignupRequest, MemberResponse, PostCreateRequest, PostResponse, MemberStatus, PostStatus)
- Day 2: sealed interface 변환 (SignupResult)
- Day 3: 도메인 클래스 변환 (Member - companion object, require/check)
- Day 4: extension function 변환 (MemberMapper)
- Day 5: 종합 리팩토링 + docs 정리

## 디렉토리 구조
- java-samples/  → 변환 대상 Java 참고 코드 (수정하지 말 것)
- kotlin-lab/    → 내가 직접 변환한 Kotlin 코드
- docs/          → 주차별 학습 기록
