# 장소 설계

## 상세 설계
* 회원과 1대1 연관관계를 맺습니다.
* 외부식별자로 uuid를 가지며, 바이너리 16타입 입니다.
* 주소는 임베디드 타입으로 어플리케이션단에서 관리합니다.
* 전화번호와 주소가 변경가능합니다.
* 해당 도메인은 일반회원은 접근 불가능하며, 장소에 대한 검색, 조회 등만 가능합니다.
* 장소를 검색할때에는 이름과 주소로 검색이 가능하며, 주소는 시, 도로명주소, 상세로 검색됩니다.
* 주소 검색은 동적쿼리를 활용해 유연하게 반응합니다.
* 정소는 회원 탈퇴시에 벌크연산을 통해 자동삭제됩니다.

## API 설계
```
[GET] /place/detail/{uuid} : 장소 상세 조회
[GET] /place/me/{memberUUID} : 업주회원의 본인이 등록한 장소 조회
[GET] /place/home : 전체 장소 홈
[GET] /place/search/name : 이름으로 검색
[GET] /place/search/address : 주소로 검색
[POST] /place/create/{memberUUID} : 회원 uuid 받아 장소 생성
[PUT] /place/update/tel/{uuid}
[PUT] /place/update/address/{uuid}
```

## Json body 예시
```json
[CreatePlace]
{
  "name": "test_name",
  "tel": "0212345678",
  "city": "서울",
  "roadNum": "종로5가 337-1",
  "detail": "102동 101호"
}

[UpdateAddress]
{
  "city": "성남시",
  "roadNum": "대왕 판교로 123-12",
  "detail": "909동 1002호"
}

[UpdateTel]
{
  "tel": "01089776655"
}
```