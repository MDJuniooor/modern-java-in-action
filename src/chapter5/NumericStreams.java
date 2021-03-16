package chapter5;

import chapter4.Dish;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static chapter4.Dish.menu;

public class NumericStreams {
    public static void main(String[] args) {
        // boxing cost 존재
        int calories = menu.stream()
                            .map(Dish::getCalories)
                            .reduce(0, Integer::sum);
        // -> 기본형 특화 스트림을 제공

        // 5.7.1  기본형 특화 스트림
        // 기본형 특화 스트림은 오직 박싱 과장에서 일어나는 효율성과 관련 있으며, 스트림에 추가 기능을 제공하지는 않는다!

        // mapToInt
        calories = menu.stream()
                        .mapToInt(Dish::getCalories) // Return IntStream
                        .sum(); // IntStream interface에 있는 sum 메서드 (min, max, average 등등)

        // 객체 스트림으로 복원하기
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream stream = intStream.boxed(); // IntStream -> Stream (boxed)


        // 기본값: OptionalInt
        OptionalInt maxCalories = menu.stream()
                                        .mapToInt(Dish::getCalories)
                                        .max();
        int max = maxCalories.orElse(1); // 값이 없을 때 기본 최댓값을 명시적으로 설정

        // 5.7.1 숫자 범위
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                                    .filter(n -> n % 2 == 0); // [1, 100]
        System.out.println(evenNumbers.count());

        // 5.7.3 숫자 스트림 활용
        Stream<int[]> pythagoreanTriples =
            IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                            IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                .mapToObj(b -> new int[] {a, b, (int) Math.sqrt(a*a + b*b)})
                        );

        pythagoreanTriples.limit(5)
                .forEach(t ->
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        // 중복 계산 제거
        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(
                                b -> new double[]{a, b, Math.sqrt(a*a + b*b)})
                        .filter(t -> t[2] % 1 == 0));

        pythagoreanTriples2.limit(5)
                .forEach(t ->
                        System.out.println(t[0] + ", " + t[1] + ", " + t[2]));


    }
}
