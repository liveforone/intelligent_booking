# Intelligent booking

# 목차
1. [프로젝트 소개](#1-프로젝트-소개)
2. [프로젝트 설계](#2-프로젝트-설계)
3. [고민점](#3-고민점)

# 1. 프로젝트 소개
## 소개
* 해당 프로젝트는 예약 서비스 플랫폼입니다.
* **예약** 이라는 특성때문에 대부분의 이벤트들이 **시간**을 기반으로 작동합니다. 제약들도 시간을 기반으로 걸리게됩니다.
* 통합 시간을 다루는 것을 포함하여 **분할되어있는 시간**을 다루는것 까지 모두 확인하실 수 있습니다.
* **내/외부 식별자**를 구분하여 보다 확장성있는 어플리케이션을 만들었습니다.
* 동적쿼리를 적극활용하여 유연하게 사용자의 요청을 받아들이도록 설계하였습니다.
* 마지막으로 간단한 쿼리부터 제약조건, 정규화에 이르기까지 DB에서 대한 많은 고민과 도전들이 담겨있습니다.
* 이 프로젝트는 지속적으로 좋은 코드, 패턴, 효율적인 로직으로 리팩토링됩니다.
## 기술 스택
* Framework : Spring Boot 3.1.1
* Lang : Kotlin, Jvm17
* Data : Spring Data Jpa & Kotlin-Jdsl & MySql
* Security : Spring Security & Jwt
* Test : Junit5

# 2. 프로젝트 설계
## 시스템 설계
* [전체 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/DESIGN.md)
* [DB 설계](https://github.com/liveforone/intelligent_booking/blob/master/Documents/DB_DESIGN.md)
* [흐름도](https://github.com/liveforone/intelligent_booking/blob/master/Documents/FLOW.md)
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
* [이메일(아이디)은 유니크로 두지 않는다](https://github.com/liveforone/intelligent_booking/blob/master/Documents/EMAIL_NEVER_UNIQUE.md)
* [Jdsl 많은 조건에서 동적쿼리](https://github.com/liveforone/intelligent_booking/blob/master/Documents/DYNAMIC_QUERY.md)
* [UUID 사용시 No-Offset 페이징](https://github.com/liveforone/intelligent_booking/blob/master/Documents/NO_OFFSET.md)
* [Jdsl 임베디드 컬럼 꺼내는법](https://github.com/liveforone/intelligent_booking/blob/master/Documents/EMBEDED_IN_JDSL.md)
* [테이블 정규화가 자원의 낭비같이 여겨질때]()
* [단방향 관계에서 cascade 사용하기]()
* [시와 분이 분할되어있는 상황에서 시간 비교하기](https://github.com/liveforone/intelligent_booking/blob/master/Documents/COMPLEX_TIME_CONTROL.md)

https://kukekyakya.tistory.com/546

==테이블 정규화==
테이블을 정규화(쪼개는) 하는 기준은 사용자로 볼 수 있다.
일례로 주문 테이블을 만들때 주문 테이블을 일반 회원도 보고, 셀러도 본다면 사용자가 둘이다.
그런데 일반회원이 탈퇴한다면 주문테이블이 삭제되게 만들게되면 이는 셀러에게 영향을 준다.
셀러는 해당 주문 데이터를 기반으로 매출을 산정하거나 할것인데 말이다.
따라서 테이블은 쪼개질때 까지 쪼개는 것이 옳다.
사용범위나 목적이 조금이라도 달라지면 테이블을 분리하는것이 옳다.