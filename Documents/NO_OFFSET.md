# UUID 사용시 No-Offset 페이징

## id가 아닌 uuid
* 기존에는 id를 사용하여 no offset을 많이 해왔을 것이다.
* 그런데, 필자의 현 프로젝트 처럼 모든 데이터가 외부에는 uuid를 리턴하고, id(pk)는 노출하지 않는 구조라면 어떨까?
* 이 경우 어떻게 no offset 페이징을 할 수 있을까?
* uuid는 정렬도 쉽지 않은데 말이다.

## id를 조회하는 쿼리가 필요하다. 이게 최선이다.
* 맨 마지막 uuid값을 받아온다. 
* 물론 id(pk)를 활용한 no-offset과 마찬가지로 null일 경우 첫페이지로인식한다.
* 그리고 uuid를 이용해 해당 uuid의 pk를 조회하여 가져온다. 이때에는 모든 값들이 필요하지 않다.
* 성능을 최적화 하기위해 uuid에는 당연히 인덱스를 걸어야하고 필요한 데이터인 id만 가져온다.
* 그리고 그 id를 바탕으로 id보다 작은 데이터를 가져오는 조건절 쿼리를 완성하면된다.
* id(pk)를 사용할때와 비교했을때 딱 하나가 추가되었다.
* 바로 uuid를 바탕으로 id를 조회하는 로직이다.
```kotlin
private fun findLastId(lastUUID: UUID): Long {
        return queryFactory.singleQuery {
            select(listOf(col(Place::id)))
            from(Place::class)
            where(col(Place::uuid).equal(lastUUID))
        }
}

private fun <T> SpringDataCriteriaQueryDsl<T>.ltLastUUID(lastUUID: UUID?): PredicateSpec? {
    val lastId = lastUUID?.let { findLastId(it) }
    return lastUUID?.let { and(col(Place::id).lessThan(lastId!!)) }
}
```