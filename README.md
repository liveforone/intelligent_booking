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
* 마지막으로 간단한 쿼리부터 제약조건 등에 이르기까지 DB에서 대한 많은 고민과 도전들이 담겨있습니다.
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
* [단방향 관계에서 cascade 사용하기](https://github.com/liveforone/intelligent_booking/blob/master/Documents/CASCADE.md)
* [시와 분이 분할되어있는 상황에서 시간 비교하기](https://github.com/liveforone/intelligent_booking/blob/master/Documents/COMPLEX_TIME_CONTROL.md)