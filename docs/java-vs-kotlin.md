# Java vs Kotlin 비교 정리

학습하면서 채워가는 문서입니다.

## 문법 비교

| 주제 | Java | Kotlin |
|------|------|--------|
| DTO | class + getter + constructor | data class |
| null 처리 | null check / Optional | nullable type / ?. / ?: |
| static factory | static method | companion object |
| 검증 | if + throw | require / check |
| 불변 | final field | val |
| 상태 변경 | setter / method | var + method |
| Util 클래스 | static method | extension function |
| Result 타입 | 별도 클래스 | sealed interface |
| enum | enum | enum class |

## Spring 통합 함정 (2주차에 채울 것)

- [ ] kotlin-spring plugin
- [ ] @field: use-site target
- [ ] Jackson Kotlin module
- [ ] kotlin-jpa plugin

## JPA 주의점 (3주차에 채울 것)

- [ ] Entity를 data class로 만들면 안 되는 이유
- [ ] id: Long? 처리
- [ ] protected set
