# 회원 설계

## 회원관리 기술
* 세션보다 가볍고 관리하기 편한 Jwt 토큰을 활용해 서버에 부담을 최대한 줄이는 방식을 채택하였습니다.
* 토큰관리는 가장 보편적인 방식인 프론트엔드의 로컬스토리지에서 jwt토큰을 관리하는것을 전제로 합니다.
* 토큰의 만료시간은 2시간 입니다.

## 상세 요구사항
* 외부 식별자 이름은 uuid로 합니다. 다른 도메인에서 사용하게된다면 memberUUID 형태로 사용합니다.
* 회원은 president, member, admin 세 종류가 있습니다.
* president는 업주로 장소를 제어할 수 있습니다.
* 비밀번호는 모두 bcrypt로 암호화 합니다.
* 회원의 이메일과 비밀번호는 변경 가능합니다.
* 로그인은 시큐리티에 위임합니다

## API 설계
```
[POST] /member/signup
[POST] /member/signup/president : 업주 회원가입
[POST] /member/login
[GET] /member/info
[PUT] /member/{uuid}/update/email
[PUT] /member/{uuid}/update/password
[DELETE] /member/{uuid}/withdraw
```

## Json body 예시
```
[SignupRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234"
}

[LoginRequest]
{
  "email": "member1234@gmail.com",
  "pw": "1234"
}

[UpdateEmail]
{
  "newEmail": "new_email@gmail.com"
}

[UpdatePassword]
{
  "password": "1111",
  "oldPassword": "1234"
}
```