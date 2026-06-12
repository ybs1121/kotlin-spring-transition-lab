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

## Day 3: nullable/non-null 필드 요청/응답 동작 실험

### 실험

`MemberSignupRequest`에 `val nickname: String?` (default 없음, validation 없음) 추가 후 `/api/members` POST 3+2케이스 테스트.

| 케이스 | 결과 |
|---|---|
| nullable `nickname` 키 없음 | 200 OK |
| nullable `nickname: null` | 200 OK |
| nullable `nickname: "값"` | 200 OK |
| non-null `email` 키 없음 | 400, `HttpMessageNotReadableException` |
| non-null `email: null` | 400, 동일 예외 |

email 두 케이스 모두 동일 로그:
```
JSON parse error: Instantiation of [...MemberSignupRequest] value failed for JSON property email due to missing (therefore NULL) value for creator parameter email which is a non-nullable type
```

### 배운 점

- `String?` 필드: Jackson Kotlin module이 키 없음 → null로 채움. validation 없으면 그냥 통과.
- `String` (non-null) 필드: 키 없음/null 둘 다 **Jackson이 생성자 호출 전에 막음** → `HttpMessageNotReadableException` (400, generic "Bad Request" body).
- `@field:NotBlank`는 **빈 문자열("")** 만 잡는 검증. "필드 자체 없음"이나 "null"은 Validation까지 도달조차 못 함 — Jackson 역직렬화 단계에서 먼저 터짐.

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| nullable `String email` + `@NotBlank` → 키없음/null/빈문자열 모두 Validation(`MethodArgumentNotValidException`)에서 동일하게 잡힘 | non-null `String email`은 키없음/null이 Validation 전 단계(`HttpMessageNotReadableException`)에서 먼저 터짐, 빈 문자열만 Validation까지 도달 |

### 헷갈렸던 것

- 에러 응답이 둘 다 `400 Bad Request`라 겉보기엔 같아 보이지만, 예외 종류와 메시지가 다름. 클라이언트 입장에서 "필드 누락" vs "빈 값"을 구분하려면 `@ExceptionHandler(HttpMessageNotReadableException::class)`도 같이 처리해야 함 (아직 미구현, 기록만).

---

## Day 4: `@ConfigurationProperties` 생성자 바인딩

### 실험

`application.yml`에 `app.member.default-status: ACTIVE`, `app.member.max-name-length: 20` 추가.
`MemberProperties` data class에 `@ConfigurationProperties(prefix = "app.member")` 적용, `KotlinLabApplication`에 `@ConfigurationPropertiesScan` 추가 후 `MemberService`에 주입해서 println.

결과:
```
memberProperties : MemberProperties(defaultStatus=ACTIVE, maxNameLength=20)
```

### 배운 점

- Kotlin data class는 **primary constructor 파라미터에 바로** `@ConfigurationProperties` 바인딩됨. Java처럼 빈 생성자 + setter 필요 없음 (Spring Boot 2.2+ 생성자 바인딩).
- `default-status: ACTIVE` (kebab-case 문자열) → `MemberStatus` enum의 `ACTIVE`로 relaxed binding 자동 매핑.
- `@EnableConfigurationProperties(X::class)` 대신 `@ConfigurationPropertiesScan`으로 패키지 전체 스캔 가능.

### 타입 안 맞을 때 (`max-name-length: abc` → `Int`)

부팅 자체가 fail-fast:
```
APPLICATION FAILED TO START
Description:
Failed to bind properties under 'app.member.max-name-length' to int:
Property: app.member.max-name-length
Value: "abc"
Reason: failed to convert java.lang.String to int (caused by java.lang.NumberFormatException: For input string: "abc")
```

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| `@ConfigurationProperties` + 빈 생성자 + setter | data class primary constructor 그대로 바인딩 |
| `@EnableConfigurationProperties(X.class)` 개별 등록 | `@ConfigurationPropertiesScan`으로 패키지 스캔 |
| nullable 필드 + 타입 불일치 시 null/0 또는 런타임 시점 에러 | non-null 타입(`Int`, enum 등)은 바인딩 실패 시 **부팅 자체가 fail-fast** |

### 헷갈렸던 것

- 바인딩 실패가 "그 필드만 null/기본값"이 아니라 **앱 전체가 안 뜸**. non-null 생성자 파라미터라서 바인딩 못 하면 객체 자체를 못 만드니 당연한 결과긴 한데, Java의 setter 기반(필드 기본값 유지)과 체감이 다름.

---

## Day 5: `lateinit var` vs `by lazy`

### 실험

`MemberService`(싱글톤 빈)에 추가:
- `lateinit var lastSignupEmail: String` — 초기화 안 한 채로 `signup()` 시작 시 접근 시도, 이후 값 할당.
- `val configSummary: String by lazy { println("계산 중"); "default=${memberProperties.defaultStatus}" }` — `signup()`에서 2번 호출.

`signup()` 2번 호출(다른 email) 결과:

**1차 호출**
```
lateinit 예외 : lateinit property lastSignupEmail has not been initialized
lastSignupEmail (init 후) : d1@test.com
configSummary 계산 중          ← 1회만 출력
configSummary 1차 호출 : default=ACTIVE
configSummary 2차 호출 : default=ACTIVE
```

**2차 호출**
```
lastSignupEmail (init 전) : d1@test.com   ← 예외 없음, 1차에서 set한 값 유지
lastSignupEmail (init 후) : d2@test.com
configSummary 1차 호출 : default=ACTIVE   ← "계산 중" 다시 안 찍힘 (캐시됨)
configSummary 2차 호출 : default=ACTIVE
```

### 배운 점

- `lateinit var` 초기화 전 접근 → `kotlin.UninitializedPropertyAccessException: lateinit property X has not been initialized`. `!!`와 다른 종류의 NPE류 예외.
- `lateinit`은 `var`만 가능, primitive 타입(`Int`, `Long`, `Boolean` 등) 불가 — backing field가 null로 초기화 가능한 참조 타입이어야 컴파일러가 "초기화 안 됨" 상태를 표현 가능하기 때문.
- `MemberService`는 싱글톤 빈 → `lateinit var` 필드는 **모든 요청이 공유하는 mutable 상태**. 요청마다 새로 만들어지는 게 아님. 동시 요청 시 race condition 가능성.
- `by lazy`는 **빈 인스턴스 전체 라이프사이클에서 1회만** 계산 후 캐싱. 기본 `LazyThreadSafetyMode.SYNCHRONIZED`로 thread-safe.

### `@Lazy`(Spring) vs `by lazy`(Kotlin) — 별개 개념

| | `@Lazy` | `by lazy` |
|---|---|---|
| 레이어 | Spring DI 컨테이너 | Kotlin 언어(프로퍼티) |
| 늦추는 대상 | 빈 생성 시점 | 프로퍼티 값 계산 시점 |
| 같이 쓰기 | `@Lazy` 빈 내부에 `by lazy` 프로퍼티 둘 다 가능 | |

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| 필드 선언 시 기본값(null/0) 허용, 나중에 set | non-null 필드는 즉시 초기화 강제 → `lateinit`으로 예외 처리 |
| getter에서 `if (cached == null) compute()` 수동 캐싱 | `by lazy { compute() }`로 캐싱 위임 |

### 헷갈렸던 것

- `lateinit var`를 싱글톤 빈의 "요청별 임시 상태"처럼 쓰면 절대 안 됨 — 다음 요청이 이전 요청 값을 그대로 봄. (Java였어도 인스턴스 필드면 동일하게 위험하지만, `lateinit`이 "초기화 미보장" 뉘앙스라 착각하기 쉬움)
- `by lazy`가 매 호출마다 재계산되는 거 아니냐고 헷갈렸는데, **빈당 1회**임. 요청마다 다른 값이 필요하면 `by lazy` 쓰면 안 됨.

---

## 다음에 할 것

- Post API 동일 패턴 반복 (Controller/Service/Repository, nullable/Validation/생성자 바인딩 등 1~5일차 함정 재확인)
