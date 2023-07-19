# 예약 설계

## 상세 설계
* 예약은 예약을 등록한 회원에게도 속하지만, 타임테이블에도 속한다.
* 타임테이블에 속하기 때문에 장소의 주인이 타임테이블에 등록된 예약을 바탕으로 사용자를 판별하고 예약을 확인한다.
* 이러한 이유로 예약은 on delete cascade를 어떤 fk에도 걸지 않는다.
* 즉 예약은 삭제가 불가능하다.
* 예약은 상태가 존재하며, 예약과 취소 두가지 상태를 갖는다.
* 예약은 예약시간 전에만 가능하다.
* 예약 취소는 예약시간 전이면서 예약한지 1시간 이내에 가능하다. 이외에는 불가능하다.
* 예약이 발생하게되면 타임테이블에 잔여 예약 가능자수를 마이너스하며,
* 예약이 성공적으로 취소되면 타임테이블의 잔여 예약 가능자수를 플러스한다.

## API 설계
```
[GET] /reservation/detail/{uuid} : 예약 상세
[GET] /reservation/member/{memberUUID} : 회원에게 속한 예약 조회
[GET] /reservation/timetable/{timetableUUID} : 타임테이블에 속한 예약 조회
[POST] /reservation/create : 예약 신청
[PUT] /reservation/cancel/{uuid} : 예약 취소
```

## Json Body 예시
```json
[CreateReservation]
{
  "timetableUUID": "8c95c6bc-cd2a-4717-a23e-6f442bbedcc7",
  "memberUUID": "4478845c-b47c-4eed-b66c-7d0ac9a97fdc"
}
```