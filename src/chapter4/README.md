# chapter 4. 스트림 소개
- 스트림이란 무엇인가?
- 컬렉션과 스트림
- 내부 반복과 외부 반복
- 중간 연산과 최종 연산

JAVA8 Stream API 특징
- 선언형: 더 간결하고 가독성이 좋아진다.
- 조립할 수 있음: 유연성이 좋아진다.
- 병렬화: 성능이 좋아진다.

### 4.2 스트림 시작하기
스트림이란 정확히 무엇일까? 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
- 연속된 요소: 특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스를 제공. 
컬렉션은 시간과 공간의 복잡성과 관련된 요소 저장 및 접근 연산이 주를 이루나, 스트림은 filter, sorted, map처럼 표현 계산식이 주를 이룸
- 소스: 컬렉션, 배열, I/O 자원 등의 데이터 제공 소스로부터 데이터를 소비. 순서는 그대로 유지 (정렬된 데이터 -> 정렬 유지)
- 데이터 처리 연산: filter, map, reduce, find, match, sort. 순차적으로 또는 병렬로 실행 가능

스트림의 중요한 특징
- 파이프라이닝: 대부분의 스트림 연산은 스트림 연산끼리 연결해서 커다란 파이프라인을 구성할 수 있도록 스트림 자신을 반환한다. 
Laziness, short-circuiting 같은 최적화도 얻을 수 있다.
- 내부 반복: 반복자를 명시적으로 사용하는 컬렉션과 달리 내부 반복을 수행
```
List<String> threeHighCaloricDishNames =
        menu.stream()
        .filter(dish -> dish.getCalories() > 300)
        .map(Dish::getName)
        .limit(3)
        .collect(toList()); // collect 연산이 불려지기 전까지 메서드 호출이 저장된다.
```  

### 4.3 스트림과 컬렉션

데이터를 언제 계산하느냐가 컬렉션과 스트림의 가장 큰 차이다. 컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조다.
즉, 컬렉션의 모든 요소는 컬렉션에 추가하기 전에 계산되어야 한다.
반면 스트림은 이론적으로 요청할 때만 요소를 계산하는 고정된 자료구조다. (스트림에 요소를 추가하거나 제거할 수  없다.)
스트림은 게이르게 만들어지는 컬렉션과 같다. 사용자가 데이터를 요청할 때만 값을 계산한다.
- DVD 대여와 스트리밍 서비스의 차이!

#### 4.3.1 딱 한 번만 탐색할 수 있다
```
List<String> title = Arrays.asList("Java8", "In", "Action");
Stream<String> s = title.stream();
s.forEach(System.out::println); // title 출력
s.forEach(System.out::println); // 스트림이 이미 소비되었거나 닫힘 (IllegalStateException 발생)
```
- 스트림과 컬렉션의 철학적 접근: 스트림은 시간적으로 흩어진 값의 집합이고, 컬렉션은 공간적으로 흩어진 값의 집합

#### 4.3.2 외부 반복과 내부 반복
```
(1)Collection: for-each 루프를 이용하는 외부 반복
List<String> names = new ArrayList<>();
for(Dish dish: menu) {
    names.add(dish.getName());

(2)Collection:  내부적으로 숨겨졌던 반복자를 사용한 외부 반복
List<String> names = new ArrayList<>();
Iterator<Dish> iterator = menu.iterator();
while(iterator.hasNext()){
    Dish dish = iterator.next();
    names.add(dish.getName();
}
(3)Stream: 내부 반복
List<String> names = menu.stream()
                    .map(Dish::getName)
                    .collect(toList());
```
내부 반복을 이용하면 작업을 투명하게 병렬로 처리하거나 더 최적화된 다양한 순서로 처리할 수 있다.

### 4.4 스트림 연산
- fitler, map, limit 는 서로 연결되어 파이프라인을 형성한다.
- collect로 파이프라인을 실행한 다음에 닫는다.

#### 4.4.1 중간연산
- filter나 sorted 같은 중간 연산은 다른 스트림을 반환한다. 여러 중간 연산을 연결해서 질의를 만들 수 있다.
- 단말 연산을 스트림 파이프라인에 실행하기 전까지는 아무 연산도 수행하지 않는다. (Lazy)

```
List<String> names =
        menu.stream()
        .filter(dish -> {
            System.out.println("filtering: " + dish.getName());
            return dish.getCalories() > 300;
        })
        .map(dish -> {
            System.out.println("mapping: " + dish.getName());
            return dish.getName();
        })
        .limit(3)
        .collect(toList());
/*
Output:
    filtering: pork
    mapping: pork
    filtering: beef
    mapping: beef
    filtering: chicken
    mapping: chicken
    [pork, beef, chicken]
*/
System.out.println(names);
```
1) 300칼로리가 넘는 요리는 여러 개지만 오직 처음 3개만 선택되었다. (쇼트서킷 기법)
2) filter와 map은 서로 다른 연산이지만 한 과정으로 병합되었다 (루프퓨전)

#### 4.4.3 스트림 이용하기
스트림 이용 과정
- 질의를 수행할 데이터 소스
- 스트림 파이프라인을 구성할 중간 연산 연결
- 스트림 파이프라인을 실행하고 결과를 만들 최종 연산

스트림 파이프라인의 개념은 빌더 패턴과 비슷하다.

|연산|형식|반환 형식| 연산의 인수|함수 디스크립터|
|---:|---:|---:|---:|---:|
|fitler|중간연산|Stream<T>|Predicate<T>|T -> boolean|
|map|중간연산|Stream<R>|Function<T,R>|T -> R|
|limit|중간연산|Stream<T>|||
|sorted|중간연산|Stream<T>|Comparator<T>|(T,T) -> int|
|distinct|중간연산|Stream<T>|||

##### 최종연산
- forEach : 스트림의 각 요소를 소비하면서 람다를 적용
- count : 스트림의 요소 개수를 반환
- collect : 스트림을 리듀스해서 리스트, 맵, 정수 형식의 컬렉션을 만든다.
 