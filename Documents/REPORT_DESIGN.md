# 신고 설계

## 상세 설계
* 신고는 president회원이 본인의 타임테이블에서 노쇼를 한 회원들을 상대로 하는것으로,
* 회원은 5번을 초과한 신고를 받게되면 계정이 정지된다.
* 신고는 예약이 cancel된 경우에는 불가능하며,
* 예약 시간 전에는 당연하게도 신고가 불가능하다.
* 또한 예약 시간이 지난후 하루안에만 가능하다.
* 이러한 3가지의 조건을 만족할경우 신고가 정상적으로 처리된다.
* 복잡한 시간을 다루고 있으며, 이에 대해서는 고민점에 [시와 분이 분할되어있는 상황에서 시간 비교하기](https://github.com/liveforone/intelligent_booking/blob/master/Documents/COMPLEX_TIME_CONTROL.md) 항목을 확인하면 된다.

## API 설계
```
[POST] /report
```

## Json Body 예시
```json
[ReportRequest]
{
  "reservationUUID": "81b2b572-ba7d-43bd-a1a1-f681b4fda897",
  "memberUUID": "aafccf63-5a5d-4ac9-97c1-6db70e1e34e1",
  "timetableUUID": "fdf0184d-a833-4dd6-921f-e53849e86c2b"
}
```