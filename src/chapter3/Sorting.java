package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

import static chapter3.Color.GREEN;
import static chapter3.Color.RED;
import static java.util.Comparator.comparing;

public class Sorting {
    public static void main(String... args) {
        List<String> str = Arrays.asList("a","b","A","B");
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        str.sort(String::compareToIgnoreCase);

        ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s);
        Function<String, Integer> stringToInt2 = Integer::parseInt;

        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        contains = List::contains;

//        Predicate<String> startWithNumber = (String string) -> this.startsWithNumber(string);
//        startWithNumber = this::startsWithNumber;


        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, RED)
        ));

        // step1: 코드 전달
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // step2: 익명 클래스 사용
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(inventory);

        // step3: 람다 표현식 사용
        inventory.sort((Apple a1, Apple a2) ->
                a2.getWeight().compareTo(a1.getWeight()));
        System.out.println(inventory);
        // step3-2: 형식 추론 이용
        inventory.sort((a1, a2) ->
                a2.getWeight().compareTo(a1.getWeight()));

        // step3-3: comparing 정적 메서드 사용
        Comparator<Apple> c = comparing((Apple a) -> a.getWeight());
        inventory.sort(comparing(apple -> apple.getWeight()));

        // step4: 메서드 참조 사용
        inventory.sort(comparing(Apple::getWeight));
        // reverse
        inventory.sort(comparing(Apple::getWeight).reversed());

        // Comparator 연결
        inventory.sort(comparing(Apple::getWeight)
                 .reversed()
                 .thenComparing(Apple::getColor));

        // predicate 조합
        Predicate<Apple> redApple = (apple -> RED.equals(apple.getColor()));
        Predicate<Apple> notRedApple = redApple.negate();
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        Predicate<Apple> redAndHeavyAppleOrGreen = redApple
                .and(apple -> apple.getWeight() > 150)
                .or(apple -> GREEN.equals(apple.getColor()) );

        // function 조합
        Function<Integer, Integer> f = x -> x+1;
        Function<Integer, Integer> g = x -> x*2;
        Function<Integer, Integer> h = f.andThen(g);  // g(f(x))
        int result = h.apply(1); // result = 4

        h = f.compose(g); // f(g(x))
        result = h.apply(1); // result = 3

        Function<String, String> addHeader = Letter::andHeader;
        Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);
    }

    public double integrate(DoubleFunction<Double> f, double a, double b){
        return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
    }
}
