# 2주차: Kotlin/Spring Boot 통합 함정

## Day 1: Controller/Service/Repository 기본 구조

### 배운 점

- Kotlin primary constructor = Lombok `@RequiredArgsConstructor` 역할. `()` 안에 의존성 선언하면 끝.
- Spring은 생성자가 하나면 `@Autowired` 없이 자동 주입. Kotlin primary constructor가 딱 하나라서 그냥 동작.
- `val`은 외부 노출 불필요한 필드는 `private val`로 선언.
- `store[id] = value` = `put()`, `store[id]` = `get()`. Kotlin Map 인덱스 연산자.
- `any { it.email == email }` = Java `stream().anyMatch(m -> m.getEmail().equals(email))`.
- 마지막 람다 인자는 괄호 밖으로 꺼낼 수 있음: `any({ })` → `any { }`.

### @field: use-site target 함정

- data class `val email`은 동시에 **생성자 파라미터 + backing field + getter** 세 곳 해당.
- `@NotBlank`만 쓰면 Kotlin이 생성자 파라미터에 붙임 → Bean Validation은 field를 읽으므로 검증 무시됨.
- `@field:NotBlank` 로 use-site target 명시 필요.
- 경고 메시지: `The annotation is only applied to the parameter. An explicit annotation use-site target is recommended.`

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| Lombok `@RequiredArgsConstructor` | primary constructor에 직접 선언 |
| `@Autowired` 생성자 주입 | 생성자 하나면 자동 주입, 애노테이션 불필요 |
| `map.put(id, value)` | `map[id] = value` |
| `stream().anyMatch(x -> ...)` | `any { ... }` |
| `@NotBlank` 그냥 붙이기 | `@field:NotBlank` use-site target 필수 |

### 헷갈렸던 것

- 생성자를 따로 안 만드는 게 어색했음. `()` 안에 쓰는 것 자체가 생성자.
- 클래스 선언 시 `()` vs `{}` — `()`는 생성자 파라미터/의존성, `{}`는 클래스 바디(메서드·프로퍼티). 둘 다 없어도 됨.
- 컬렉션 함수 문법이 아직 어색. Stream 사고방식 버리고 컬렉션에 바로 붙이는 것에 익숙해질 필요.
- `data object Success` — 싱글톤이라 `Success(email)` 형태로 호출 불가. `SignupResult.Success`로 참조.
---

## 다음에 할 것

- `GET /api/members/{id}` 조회 API — `@PathVariable`, nullable 처리
- nullable/non-null 필드 요청/응답 동작 실험
- `@ConfigurationProperties` 생성자 바인딩
- `lateinit var` vs `by lazy`
- Post API 동일 패턴 반복
