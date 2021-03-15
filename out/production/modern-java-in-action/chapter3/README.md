### 3.1 람다란 무엇인가?

#### 람다의 표현식
```
1) (parameters) -> expression
2) (parameters) -> { statements; }
```

#### 람다 예제
```
1) boolean expression
(List<String> list) -> list.isEmpty()
2) Object Creation
() -> new Apple(10)
3) Cunsumption in Object
(Apple a) -> {
    system.out.println(a.getWeight();
}
4) Selection/Extraction in Object
(String s) -> s.length()
5) Combination with two value
(int a, int b) -> a * b
6) Compare to two value
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
```

### 3.2 어디에, 어떻게 람다를 사용할까?

#### 3.2.1 함수형 인터페이스
함수형 인터페이스? 정확히 하나의 추상 메서드를 지정하는 인터페이스
ex) Comparable, Runnable

*인터페이스는 디폴트 메서드(기본 구현을 제공하는 바디를 포함하는 메서드)를 포함할 수 있다. 많은 디폴트 메서드가 있더라도 추상 메서드가 오직 하나면 함수형 인터페이스다.

함수형 인터페이스로 할 수 있는 일? 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다.

#### 3.2.2 함수 디스크립터
함수 디스크립터: 람다 표현식의 시그니처를 서술하는 메서드, 함수형 인터페이스의 추상 메서드 시그니
() -> void
(Apple) -> boolean

@FuntionalInterface: 함수형 인터페이스임을 가리키는 인터페이스. 추상 메서드가 두 개 이상이면 에러를 반환


### 3.3 실행 어라운드 패턴
실행 어라운드 패턴: setup -> job -> cleanup 단계를 가지고 있는 패턴

*실행 어라운드 패턴을 적용하는 네 단계 과정
```
    1) 기존 메서드
    public String processFile() throws IOException {
        try(BufferedReader br = new BufferedReader((new FileReader("data.txt")))){
            return br.readLine();
        }
    }

    2) 함수형 인터페이스 구현
    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }

    3) 함수형 인터페이스를 파라미터로 받는 메서드 구현
    public String processFile(BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }

    4) 해당 메소드 사용
    public static void main(String[] args) throws IOException {
        // read one line
        String result = processFile((BufferedReader br) -> br.readLine());
        
        // read two line
        String result2 = processFile((BufferedReader br) -> br.readLine() + br.readLine());
    }

```

### 3.4 함수형 인터페이스 사용
기본적인 제네릭 함수형 인터페이스(Predicate<T>, Consumer<T>, Function<T,R>)를 사용할 때는
primitive type은 사용할 수 없다. (autoboxing이 되지만, 메모리 부분에서 비용이 듦)
기본형 특화 인터페이스가 존재한다. (IntPredicate, LongPredicate 등)

Function<T,R>: T 객체를 받아 R 객체로 리턴함

함수형 인터페이스는 확인된 예외를 던지는 동작을 허용하지 않는다. 예외를 던지는 람다표현식을 만들려면 학인된 예외를 선언하는 함수형 인터페이스를 직접 정의하거나 람다를 try/catch
블록으로 감싸야한다.

### 3.5 형식 검사, 형식 추론, 제약

#### 3.5.1 형식검사
람다가 사용되는 context를 이용하에서 람다의 형식을 추론할 수 있다. 어떤 context에서 기대되는 람다 표현식의 형식을 target type이라고 부른다.

ex) 

```
List<Apple> heavierThan150g = filter(inventory, (Apple apple) -> apple.getWeight() > 150);

1) filter 정의 검사
filter(List<Apple> inventory, Predicate<Apple> p);
2) target type 확인 
Predicate<Apple>
3) Predicate<Apple> 인터페이스의 추상메서드 확인
boolean test(Apple apple)
4) 시그니처 체크
Apple -> boolean
*람다표현식이 예외를 던질 수 있다면, 추상 메서드도 같은 예외를 던질 수 있도록 throws 선언해야 한다.
```

#### 3.5.2 같은 람다, 다른 함수형 인터페이스
```
Callable<Integer> c = () -> 42;
PrivilegedAction<Integer> p = () -> 42;

*특별한 void 호환 규칙
람다의 바디에 일반 표현식이 있으면 void를 반환하는 함수 디스크립터와 호환된다.
예를 들어 List의 add 메서드는 Consumer context(T -> void)가 기대하는 void 대신 boolean을 반환하지만 유효한 코드다.
Comsumer<String> b = s -> list.add(s);
```

#### 3.5.3 형식 추론
```
// 형식 추론 X
Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()); 

// 형식 추론 O
Comparator<Apple> c = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight()); 
```

#### 3.5.4 지역 변수 사용
람다 캡쳐링: 외부에서 정의된 변수를 활용하여 사용하는 것
*단, 지역 변수는 명시적으로 final로 선언되어 있어야 하거나 실질적으로 final로 선언된 변수와 똑같이 사용되어야 한다.
```
// OK
int portNumber = 1337;
Runnable r = () -> System.out.println(portNumber);

// Compile Error
int portNumber = 1337;
Runnable r = () -> System.out.println(portNumber);
portNumber = 31337;
```
why? 인스턴스 변수는 힙에 저장되는 반면 지역 변수는 스택에 위치한다. 람다가 스레드에서 실행된다면 변수를 할당한 스레드가 사라져서
변수 할당이 해제되었는데도 람다를 실행하는 스레드에서는 해당 변수에 접근하려 할 수 있다.
따라서 자바 구현에서는 원래 변수에 접근을 허용하는 것이 아니라 자유 지역 변수의 복사본을 제공한다.
복사본의 값이 바뀌지 않아야 하므로 지역 변수에는 한 번만 값을 할당해야 한다는 제약이 생긴 것이다.

#### 3.6 메서드 참조
```
inventory.sort((Apple a1, Apple a2) ->
                a1.getWeight().compareTo(a2.getWeight());
// 메서드 참조
inventory.sort(comparing(Apple::getWeight));
```

- 메서드 참조를 만드는 방법
1) 정적 메서드 참조
Integer::parseInt
2) 다양한 형식의 인스턴스 메서드 참조
String::length
(String s) -> s.toUpperCase() == String::toUpperCase
3) 기존 객체의 인스턴스 메서드 참조
Transaction 객체를 할당받은 expensiveTransaction 지역 변수가 있고 Transaction 객체에는 getValue 메서드가 있다면,
expensiveTransaction::getValue라고 표현할 수 있다.


*세 가지 종류의 람다 표현식을 메서드 참조로 바꾸는 방법
1) (args) -> ClassName.staticMethod(args)
=> ClassName::staticMethod
2) (arg0, rest) -> arg0.instanceMethod(rest)
=> ClassName::instanceMethod
3) (args) -> expr.instanceMethod(args) 
=> expr::instanceMethod

#### 3.6.2 생성자 참조
