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

## Day 2: 회원 조회 API + nullable / `!!` 함정

### 배운 점

- `@PathVariable id: Long` — non-null 경로변수. Java `Optional` 처리 불필요. 못 찾으면 Repository에서 예외.
- Java `Optional.orElseThrow()` 대응은 Kotlin **Elvis(`?:`)** + `throw`.
  - `throw`는 `Nothing` 타입이라 Elvis 우변에 바로 올 수 있음.
- `?.let { }` 과 `?:` 혼동 주의 — `?.let`은 "값 있으면 실행", `?:`는 "null이면 실행". 의미 반대.

### `!!` 함정 (JPA 사고방식의 부작용)

- `Member.id: Long?` (nullable) → 조회 후 `member.id!!` 로 강제 언래핑함.
- 근데 `Repository.save`가 `Member.signup(...)`을 **다시 호출**해서 id=null인 객체를 또 만들어 저장 → 꺼낸 객체의 id가 항상 null → `!!`에서 NPE.
- 원인: JPA였으면 `@GeneratedValue`가 채번해주지만, in-memory Map은 id를 직접 채워줘야 함.
- `!!`는 컴파일러 null 체크를 우회할 뿐, 실제 null이면 그대로 터짐. "절대 null 아님" 보장이 틀리면 NPE.

### `save`가 객체를 두 번 생성하던 문제

- 기존 `save(member)`가 인자로 받은 `member`를 안 쓰고 `Member.signup(...)`으로 새 객체를 또 만듦.
  - JPA로 치면 `save(entity)` 안에서 `new Entity(...)` 또 하는 꼴. 들어온 객체를 버림.
- `signup`은 "가입"이라는 도메인 행위 → 항상 신규, ACTIVE, id=null. 저장 시 id 부여 용도로 쓰면 의미가 퇴색됨.
- 해결: companion object에 **`reconstruct(id, ...)`** factory 분리.
  - `signup` = 최초 가입(id=null), `reconstruct` = 저장소 채번 id로 복원.
  - 검증 로직은 `private fun validate()`로 공통화 (companion object의 private = JVM `static private`).

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| `Optional<Member>` 반환 | nullable `Member?` 또는 못 찾으면 throw |
| `optional.orElseThrow(() -> ex)` | `store[id] ?: throw ex` (Elvis) |
| `@GeneratedValue` 자동 채번 | in-memory는 `reconstruct`로 직접 id 부여 |
| static 팩토리 메서드 중복 검증 | companion `private fun validate()` 공통화 |

### 헷갈렸던 것

- `?.let { throw }` 로 orElseThrow 흉내내려다 로직 반대로 짬 (값 있을 때 throw). Elvis가 정답.
- JPA 사고방식으로 `id!!` 썼다가 in-memory에선 id가 안 채워져 NPE. 채번 책임을 직접 져야 함을 체감.
- `reconstruct`는 "복원"인데 status를 ACTIVE로 고정 → WITHDRAWN 회원 복원 시 의미 빵꾸. (학습용이라 일단 둠, 의미만 기록)

---

## 다음에 할 것

- nullable/non-null 필드 요청/응답 동작 실험
- `@ConfigurationProperties` 생성자 바인딩
- `lateinit var` vs `by lazy`
- Post API 동일 패턴 반복
