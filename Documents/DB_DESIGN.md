# DB 설계

## ER-Diagram
![erd](https://github.com/liveforone/intelligent_booking/assets/88976237/b2ba2a71-0235-434d-a2d3-5830f32383e3)

## 테이블 생성 및 제약조건 명시
### 회원 -> Member
```
create table member (
    id bigint not null auto_increment,
    uuid BINARY(16) not null UNIQUE,
    auth varchar(255) not null,
    report bigint not null,
    email varchar(255) not null,
    password varchar(100) not null,
    primary key (id)
);
CREATE INDEX uuid_idx ON member (uuid);
CREATE INDEX email_idx ON member (email);
```
### 장소 -> Place
```
create table place (
        id bigint not null auto_increment,
        uuid BINARY(16) not null UNIQUE,
        member_id bigint,
        city varchar(255) not null,
        detail varchar(255) not null,
        name varchar(255) not null,
        road_num varchar(255) not null,
        tel varchar(255) not null,
        primary key (id),
        foreign key (member_id) references Member (id) on delete cascade
);
CREATE INDEX uuid_idx ON Place (uuid);
CREATE INDEX member_id_idx ON Place (member_id);
CREATE INDEX name_idx ON Place (name);
CREATE INDEX address_idx ON Place (city, road_num, detail);
```
### 예약 -> Reservation
```
create table reservation (
        created_date_time datetime(6) not null,
        id bigint not null auto_increment,
        uuid BINARY(16) not null UNIQUE,
        member_id bigint,
        timetable_id bigint,
        reservation_state varchar(255) not null,
        primary key (id),
        foreign key (member_id) references Member (id),
        foreign key (timetable_id) references Timetable (id)
);
CREATE INDEX uuid_idx ON Reservation (uuid);
CREATE INDEX member_id_idx ON Reservation (member_id);
CREATE INDEX timetable_id_idx ON Reservation (timetable_id);
```
### 리뷰 -> Review
```
create table review (
        created_date datetime(6) not null,
        id bigint not null auto_increment,
        uuid BINARY(16) not null UNIQUE,
        member_id bigint,
        place_id bigint,
        content varchar(255) not null,
        primary key (id),
        foreign key (member_id) references Member (id),
        foreign key (place_id) references Place (id) on delete cascade
);
CREATE INDEX uuid_idx ON Review (uuid);
CREATE INDEX member_id_idx ON Review (member_id);
CREATE INDEX place_id_idx ON Review (place_id);
```
### 타임테이블 -> Timetable
```
create table timetable (
        basic_count bigint not null,
        id bigint not null auto_increment,
        uuid BINARY(16) not null UNIQUE,
        place_id bigint,
        remaining_count bigint not null,
        reservation_hour bigint not null,
        reservation_minute bigint not null,
        description varchar(255) not null,
        primary key (id),
        foreign key (place_id) references Place (id) on delete cascade
);
CREATE INDEX uuid_idx ON Timetable (uuid);
CREATE INDEX place_id_idx ON Timetable (place_id);
```

## no-offset 페이징
* 페이징 성능을 향상하기 위해 no-offset 방식으로 페이징 처리한다.
* 이에 따라 동적쿼리 구성이 필요하다.
* 아래는 jdsl로 구성한 no-offset 동적쿼리이다.
* 현재 정렬은 desc이기 때문에 asc를 사용한다면 lessThan을 greaterThan으로 변경한다.
* 정책은 lastId가 null 일경우 첫 페이지로 인식하고
* 그 이외에는 lastId보다 작은 id에 한해 조회한다.
```kotlin
private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(엔티티::id)))
            from(엔티티::class)
            where(col(엔티티::uuid).equal(lastUUID))
        }
    }

    private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
        val lastId = lastUUID?.let { findLastId(it) }
        return lastUUID?.let { and(col(엔티티::id).lessThan(lastId!!)) }
    }
```