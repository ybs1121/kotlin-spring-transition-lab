# 1주차: Kotlin 핵심 문법

## Day 1: DTO 변환

### 배운 점

- 

### Java와 다른 점

| Java                   | Kotlin |
|------|--------|
| class + constructor + getter | data class + primary constructor |
| getDescription() 직접 작성 | val description 자동 getter |
| final field | val |
| nullable 가능성이 코드에 드러나지 않음 | Long? / String? 으로 타입에 드러남 

### 헷갈렸던 것

- Kotlin enum에서 `val description`으로 선언하면 프로퍼티에 대한 getter가 자동 생성된다.
- Kotlin 코드에서는 `status.description`처럼 프로퍼티로 접근하지만, JVM 바이트코드에는 `getDescription()` 메서드가 생성되어 Java에서도 호출할 수 있다.

---

## Day 2: sealed interface

### 배운 점

- 객체로 상태값을 표기 할때 활용한다.
- 자바의 inner class 사용 할때랑 비슷한 것 같기도 하다.

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| `static SignupResult success()` 팩토리 메서드 | `SignupResult.Success` 싱글톤 직접 참조 |
| `static SignupResult fail(String msg)` 팩토리 메서드 | `SignupResult.Fail("msg")` 생성자가 팩토리 역할 |
| `if (result.isSuccess())` boolean 체크 | `when (result) { is Success -> ... }` 타입으로 분기 |
| 한 클래스에 모든 케이스 담음 | 케이스마다 별도 타입으로 표현 |

### 헷갈렸던 것
- sealed, object 키워드가 명확하게 무슨 역할을 하는지 헷갈렸다. 
- boolean success 가 사라진 이유(타입 자체가 상태를 표현)
---

## Day 3: companion object, require/check

### 배운 점

- `companion object` 안에 팩토리 메서드를 두면 Java의 `static` 메서드와 동일한 역할을 한다.
- `private constructor`로 외부 직접 생성을 막고, `companion object`의 `signup`으로만 생성을 강제할 수 있다.
- `require`는 파라미터 검증 (`IllegalArgumentException`), `check`는 상태 검증 (`IllegalStateException`) — `if + throw` 축약형.
- 단, 커스텀 예외를 던져야 하는 실무에서는 `if + throw` 그대로 쓰는 게 낫다.
- `val`은 getter만 자동 생성, `var`는 getter + setter 둘 다 생성.
- `private set`으로 외부 읽기는 허용하되 외부 대입을 막을 수 있다. 단, primary constructor 안에서는 못 쓰고 클래스 바디에서 재선언해야 한다.

### Java와 다른 점

| Java | Kotlin |
|------|--------|
| `private` 필드 + `getXxx()` getter 메서드 | `val` 프로퍼티 (getter 자동 생성, 별도 메서드 불필요) |
| `static` 팩토리 메서드 | `companion object` 안에 함수 |
| `private` 생성자 | `private constructor` |
| `if (조건) throw new IllegalArgumentException(...)` | `require(조건) { "메시지" }` |
| `if (조건) throw new IllegalStateException(...)` | `check(조건) { "메시지" }` |

### 헷갈렸던 것

- `private` 필드 + getter 메서드를 그대로 썼는데, Kotlin에서는 `val`이 그 역할을 한다. `private` 필드에 getter 메서드 만들면 public이랑 결과 동일 — 모순.
- `private set`은 primary constructor 파라미터에 붙이는 게 아니라, 클래스 바디에서 프로퍼티를 재선언해야 한다.
- `require`/`check`는 Kotlin 표준 예외만 던지므로 커스텀 예외 체계가 있으면 사용 불가.

---

## Day 4: extension function

### 배운 점

- 

---

## Day 5: scope function 적용

### 배운 점

- 

---

## 최종 회고

### 잘 됐던 것

### 어색했던 것

### 다음 주 가져갈 것
