# kotlin-spring-transition-lab

## 학습자 배경
- Java/Spring Boot 4년차 백엔드 개발자
- MyBatis, QueryDSL, PostgreSQL, Redis 사용 경험
- Spring Controller-Service-Repository, 도메인 모델링, 예외처리 숙련
- Kotlin 기초 문법 학습 완료 (nullable, data class, sealed, extension function, scope function, lambda 등)

## 프로젝트 목적
게시판을 잘 만드는 게 아니라,
Java 개발자가 Kotlin/Spring에서 헷갈릴 지점을 직접 밟아보는 프로젝트

## 전체 커리큘럼
- 1주차: Kotlin 핵심 문법 변환 실습 ✅
- 2주차: Kotlin/Spring Boot 통합 함정
- 3주차: Kotlin/JPA
- 4주차: 테스트 + Coroutine 맛보기 + 최종 정리

## 현재 진행
2주차: Kotlin/Spring Boot 통합 함정

## 2주차 학습 목표
Java/Spring에서는 없던 Kotlin/Spring 특유의 함정을 직접 밟아보기

## 2주차 학습 범위
- kotlin-spring plugin (allopen) - 왜 필요한지
- kotlin-jpa plugin (noarg) - 왜 필요한지
- @field: use-site target - Validation 애노테이션
- Jackson Kotlin module - @RequestBody data class 역직렬화
- @ConfigurationProperties 생성자 바인딩
- nullable과 non-null 필드의 요청/응답 동작
- lateinit var vs by lazy
- @Transactional + kotlin-spring plugin 관계

## 2주차 구현 기능
- 회원가입 API
- 회원 조회 API
- 게시글 작성 API
- 게시글 조회 API

단, CRUD 완성보다 아래를 확인하는 게 목적
- @RequestBody data class가 정상 역직렬화되는가?
- Validation이 실제로 동작하는가?
- nullable과 non-null 필드가 요청에서 어떻게 동작하는가?

## Claude 행동 규칙

### 절대 하지 말 것
- 요청하지 않은 Kotlin 코드 먼저 작성 금지
- 정답 바로 주지 말고 힌트 먼저
- 한 번에 여러 개념 섞어서 설명 금지

### 항상 할 것
- 내가 짠 코드 리뷰 요청하면 피드백
- "동작은 하지만 Kotlin스럽지 않은" 지점 짚어줄 것
- 리뷰 시 Good / Improve / Kotlin-specific 세 항목으로 구분
- 함정은 미리 알려주지 말고, 직접 밟은 다음 설명

## 디렉토리 규칙
- java-samples/ → 수정 금지 (원본 보존)
- kotlin-lab/   → 직접 작성하는 공간
- docs/         → 주차별 학습 기록

## 2주차 Java 샘플 위치
java-samples/src/main/java/com/example/sample/
├── member/
│   ├── MemberController.java
│   ├── MemberService.java
│   └── MemberRepository.java
└── post/
├── PostController.java
├── PostService.java
└── PostRepository.java

## 2주차 시작 방법
1. java-samples에 위 Java 샘플 파일 생성 (Claude Code에게 요청)
2. kotlin-lab에 직접 Kotlin으로 구현
3. 함정 항목 하나씩 밟아보고 docs/week-02-spring-kotlin-traps.md에 기록