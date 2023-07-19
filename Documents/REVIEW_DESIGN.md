# 리뷰 설계

## 상세 설계
* 리뷰는 책임감 있는 리뷰를 위해서 소위 박제라고 불리는 한번 등록후 삭제 및 수정이 불가능하게 한다.
* 리뷰는 장소가 삭제될때만 삭제가 이루어진다.(on delete cascade)
* 리뷰는 사용자가 나의 예약에서 장소에 대한 리뷰를 등록한다.
* 이렇게 api를 제공함은 예약한 사용자만 리뷰를 등록할 수 있도록 하기 위함이다. 
* 물론 예약 하였는지 한번 더 코드상으로 체크한다.

## API 설계
```
[GET] /review/detail/{uuid} : 리뷰 상세 조회
[GET] /review/member/{memberUUID} : 회원에 속한 리뷰 조회
[GET] /review/place/{placeUUID} : 장소에 속한 리뷰 조회
[POST] /review/create : 리뷰 등록
```

## Json Body 설계
```json
[CreateReview]
{
  "reservationUUID": "5ef3978c-1c83-41e7-9f68-46af54410bf4",
  "placeUUID": "09e055bd-74c8-4594-bc87-e75387b09d10",
  "memberUUID": "718d63a0-8fab-43dd-ab46-c817517d0247",
  "content": "test_content"
}
```