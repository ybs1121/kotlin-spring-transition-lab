# kotlin-spring-transition-lab

Java/Spring Boot 4년차 개발자가 Kotlin/Spring Boot 코드베이스에 적응하기 위한 학습 프로젝트입니다.

## 학습 방식

1. `java-samples/`에 있는 Java 코드를 읽는다
2. `kotlin-lab/`에 직접 Kotlin으로 변환한다
3. Claude Code에서 리뷰를 받는다
4. `docs/`에 배운 점을 정리한다

## 프로젝트 구조

```
kotlin-spring-transition-lab/
├── java-samples/       # 변환 대상 Java 코드 (수정하지 말 것)
├── kotlin-lab/         # 직접 작성하는 Kotlin 코드
└── docs/               # 주차별 학습 기록
```

## IntelliJ 열기

1. IntelliJ에서 `File > Open` → 이 폴더 선택
2. Gradle 프로젝트로 인식되면 `Load Gradle Project` 클릭
3. Gradle이 없으면 IntelliJ가 자동으로 다운로드 제안함

## 변환 과제 순서

| Day | 파일 | 핵심 문법 |
|-----|------|-----------|
| 1 | MemberSignupRequest, MemberResponse, PostCreateRequest, PostResponse | data class, val/var |
| 1 | MemberStatus, PostStatus | enum class |
| 2 | SignupResult | sealed interface |
| 3 | Member | companion object, require/check |
| 4 | MemberMapper | extension function |
| 5 | 전체 리팩토링 | scope function 적용 |

## 커밋 규칙

```
feat: convert MemberSignupRequest to kotlin
refactor: apply scope function to member mapper
docs: summarize week-01 kotlin syntax
```
