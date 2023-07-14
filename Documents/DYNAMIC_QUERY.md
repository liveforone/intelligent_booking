# Jdsl 많은 조건에서 동적쿼리

## Jdsl에서 많은 조건의 동적쿼리는 어떻게 작성할까?
* 현 프로젝트에서 place 엔티티는 주소 데이터를 상세하게 가지고 있다.
* 총 3가지로 구성되는데, city(도시), roadNum(도로명 주소), detail(상세 주소)로 구성된다.
* 또한 주소로 place를 검색할 수 있는 api를 제공한다.
* 이때 도시만으로, 혹은 도시와 도로명 주소만으로, 혹은 3조건 모두, 혹은 도로명 주소만으로..
* 이런식으로 3조건을 여러방식으로 혼합하여 검색을 가능하게 하였다.
* 즉 하나 혹은 두개의 주소를 사용하거나, 혹은 모든 주소를 사용하여 검색을 가능하게 하였다.

## 코드로 설명
* 아래 코드에서 where(ltLastUUID()) 부분은 [UUID 사용시 No-Offset]()에서 설명하고 있으니 해당 문서를 참고 바란다.
* 또한 associate의 경우 [Jdsl 임베디드 컬럼 꺼내는법]()을 참고바란다.
* 모든 검색값들을 nullable하게 입력받았다.
* 그리고 searchAddress라는 동적쿼리 함수를 호출한다.
* 해당 함수는 and() 쿼리에 조건들을 담아서 리턴하는데,
* 각 값들이 null이라면 null 자체를 리턴한다.
* **criteria 에서 null인 where절은 무시한다.** -> jdsl은 criteria api 기반이다.
* null이 아니라면 like, 검색 쿼리를 이용해 검색한다.
* 결론적으로 코틀린의 ?.let {} 스코프 함수를 이용해서 간단하게 null처리를 하여 동적쿼리를 만들었다.
* 다양한 형태의 동적쿼리가 있지만, 일반적으로 null을 활용한 조건 무시 동적쿼리를 많이 사용한다.
* 이 경우 아래처럼 활용할 수 있다.
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

private fun <T> SpringDataCriteriaQueryDsl<T>.searchAddress(
    city: String?,
    roadNum: String?,
    detail: String?
): PredicateSpec {
    val searchCity = city?.let { col(Address::city).like("$city%") }
    val searchRoadNum = roadNum?.let { col(Address::roadNum).like("$roadNum%") }
    val searchDetail = detail?.let { col(Address::detail).like("$detail%") }
    return and(searchCity, searchRoadNum, searchDetail)
}
```