# Intelligent booking

# 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 설계](#2-프로젝트-설계)
3. [고민점](#3-고민점)

# 1. 프로젝트 소개
## 소개
* 해당 프로젝트는 예약 서비스 플랫폼입니다.
* 예약 이라는 특성때문에 대부분의 이벤트들이 시간을 기반으로 작동합니다. 제약들도 시간을 기반으로 걸리게됩니다.
* 통합 시간을 다루는 것을 포함하여 분할되어있는 시간을 다루는것 까지 모두 확인하실 수 있습니다.
* 내/외부 식별자를 구분하여 보다 확장성있는 어플리케이션을 만들었습니다.
* 또한 확장성 있는 어플리케이션을 위해 특정기술과 도메인끼리의 종속을 최대한 줄이기 위해 노력하였고, 이는 고민점에 기술되어있습니다.
* 동적쿼리를 적극활용하여 유연하게 사용자의 요청을 받아들이도록 설계하였습니다.
* 마지막으로 동시성 문제를 좋은 성능을 유지하며 해결하기 위해 쿼리를 이용한 동시성 제어를 고안하였습니다.
* 이 프로젝트는 지속적으로 좋은 코드, 패턴, 효율적인 로직으로 리팩토링됩니다.
## 기술 스택
* Framework : Spring Boot 3.1.1 향후 버전 업
* Lang : Kotlin, Jvm17
* Data : Spring Data Jpa & Kotlin-Jdsl & MySql
* Security : Spring Security & Jwt
* Test : Junit5

# 2. 프로젝트 설계
## 시스템 설계
* [전체 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/DESIGN.md)
* [DB 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/DB_DESIGN.md)
* [흐름도]()
## 엔티티 설계
* [회원 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/MEMBER_DESIGN.md)
* [장소 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/PLACE_DESIGN.md)
* [타임 테이블 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/TIMETABLE_DESIGN.md)
* [예약 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/RESERVATION_DESIGN.md)
* [신고 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/REPORT_DESIGN.md)
* [리뷰 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/REVIEW_DESIGN.md)

# 3. 고민점
## 기술적 고민
* [내부식별자와 외부 식별자 구분](https://github.com/liveforone/intelligent_booking/blob/master/Documents/INTERNAL_EXTERNAL_ID.md)
* [이메일(아이디)은 유니크로 두지 않는다]() -> 외무 종속성 피하려고
* [Jdsl 많은 조건 동적쿼리 및 컨트롤러 파라미터]() -> place에 address 동적쿼리로 설명
* [JWT에 종속적이지 않게 만들기]()
* [UUID 사용시 No-Offset]() -> timetable에 동적쿼리로 설명
* [Jdsl 벌크연산]() -> 롤백쿼리는 jpql로 직접 날림. 이유는 jdsl이 set 파라미터에서 컬럼값을 받지 않아서 벌크연산 처리할때에는 1차캐시를 무시하기때문에 clearAutomatically = true를 하여 clear해준다. 벌크시에는 삭제건 업데이트건 join이 안되기때문에 엔티티자체를 파라미터로 받아서 처리하면 비슷한 효과를 낼 수 있다.
* [Jdsl 임베디드 컬럼 꺼내는법]()
* [테이블 정규화가 자원의 낭비같이 여겨질때]()
* [단방향 관계에서 cascade 사용하기]()
* [시와 분이 분할되어있는 상황에서 시간 비교하기](https://github.com/liveforone/intelligent_booking/blob/master/Documents/COMPLEX_TIME_CONTROL.md)
* [단일 컬럼 조회시 jdsl 주의점]() -> listof 사용금지
## 비즈니스적 고민


==테이블 정규화==
테이블을 정규화(쪼개는) 하는 기준은 사용자로 볼 수 있다.
일례로 주문 테이블을 만들때 주문 테이블을 일반 회원도 보고, 셀러도 본다면 사용자가 둘이다.
그런데 일반회원이 탈퇴한다면 주문테이블이 삭제되게 만들게되면 이는 셀러에게 영향을 준다.
셀러는 해당 주문 데이터를 기반으로 매출을 산정하거나 할것인데 말이다.
따라서 테이블은 쪼개질때 까지 쪼개는 것이 옳다.
사용범위나 목적이 조금이라도 달라지면 테이블을 분리하는것이 옳다.


==jwt 고민1==
jwt 토큰 파싱은 사실상 백엔드만 가능하다. 
만약 파라미터로 jwt 토큰 안에 있는 user id를 넣기 원한다면,
한번쯤은, 혹은 api를 제공하여 user id를 클라이언트에게 전달하여야한다.
필요할때 콜하는 방식, 혹은 마이페이지 등에서 한번 정도 토큰을 까서 전달한다.

==jwt 고민2==
헤더에 jwt 토큰이 있더라도 파라미터로 전달해야할까?
검증을 위해 한번 더 하는 것은 좋다고 생각
jwt 종속성을 피하기 위해, 회원 도메인 이외에는 api에 파라미터로 받아서 사용한다. 파싱객체 사용안한다.