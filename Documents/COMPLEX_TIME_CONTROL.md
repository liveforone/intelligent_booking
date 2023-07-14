# 시와 분이 분할되어있는 상황에서 시간 비교하기

## 소개
* 시간을 다루는 방법은 여러가지가 있다.
* LocalDateTime처럼 시간을 하나의 객체에 추상화하여 다루는 경우,
* 자바와 코틀린에서 지원하는 유틸 함수를 사용할 수 있어 편리하다.
* 그러나 이러한 통합 시간은 DB단에서 검색이나 조건절로 사용하기에는 나쁘다.
* 이러한 이유로 시간을 분할하여 사용하는 경우가 있다.
* 해당 프로젝트가 그러하며, 그 중에서도 타임테이블에서 예약 시간과 예약 분을 다룰때 시와 분을 분할하여 처리한다.

## 어떻게 시간을 비교할 수 있을까?
* 현재시간을 파악하는 제일 좋은 방법은 LocalDateTime.now() 일것이다.
* 이 값에서 hour과 minute을 가져오는 것은 어렵지않다.
* 다만 LocalDateTime 끼리의 비교는 상당히 쉬운데, hour과 minute의 형태로 비교하는 것은 어렵다.
* 아래는 비교 방법을 나타냈다.
```kotlin
if (hour == nowHour) 는 분끼리 비교한다.
else 일때는 시간끼리 비교한다.
```

## 신고시 시간 비교
* 신고할때에는 현재 시간이 예약시간 보다 크면서, 예약 시간에 하루를 더한 날보다는 적어야한다.
```kotlin
val now = LocalDateTime.now()
val reservationHour = timetable.reservationHour
val reservationMinute = timetable.reservationMinute
if (now.hour == reservationHour.toInt()) {  //1. 시간이 같을때에는 분끼리 비교한다.
    check(now.minute > reservationMinute)  //2. 현재 분이 예약 분 보다 클때
} else {  //3. 시간이 같지 않다면
    check(now.hour > reservationHour)  //4. 현재 시간이 예약 시간보다 커야함
}
```

## 결론
* 분할되어있는 시와 분은 통합 시간보다 비교하기 까다롭지만,
* DB에서 조건절로 활용하기 아주 유용하다. 또한 검색도 쉬워진다.
* 따라서 이러한 활용이 필요한 경우에는 시간과 분을 분할하여 사용하는 것도 방법이다.
* 더 나아가 날짜 또한 필요하다면 분할하여 사용할 수 있다.