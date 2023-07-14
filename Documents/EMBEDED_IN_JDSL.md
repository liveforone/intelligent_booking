# Jdsl 임베디드 컬럼 꺼내는법

## 임베디드 타입 꺼내기
* jdsl은 associate() 이란 함수를 지원한다.
* 이는 일반적으로 join이 불가한 update나 delete 쿼리에서 연관 테이블의 값을 가져올때 사용되지만,
* 임베디드 타입을 꺼낼때도 사용된다.

## 코드로 보기
* place에는 address라는 임베디드 타입의 값이 있다.
* 이는 그대로 조회를 하되, associate라는 함수를 사용해서 임베디드 타입임을 명시해주어야한다.
* associate(엔티티::class, 임베디드값::class, on(엔티티::임베디드값))
* 과 같은 형태로 사용가능하다.
```kotlin
override fun searchByAddress(city: String?, roadNum: String?, detail: String?, lastUUID: UUID?): List<PlaceInfo> {
        return queryFactory.listQuery {
            select(listOf(
                col(Place::uuid),
                col(Place::name),
                col(Place::tel),
                col(Place::address)
            ))
            from(Place::class)
            associate(Place::class, Address::class, on(Place::address))
            where(searchAddress(city, roadNum, detail))
            where(ltLastUUID(lastUUID))
            orderBy(col(Place::id).desc())
            limit(PlaceRepositoryConstant.LIMIT_PAGE)
        }
}
```