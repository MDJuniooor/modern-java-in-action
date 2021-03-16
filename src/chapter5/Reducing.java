package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static chapter4.Dish.menu;

public class Reducing {
    public static void main(String[] args) {
        // 5.5.1 요소의 합
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        // 초깃값 없음
        Optional<Integer> sum2 = numbers.stream().reduce((a,b) -> (a + b ));

        // 5.5.2 최댓값과 최솟값
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        Optional<Integer> min = numbers.stream().reduce(Integer::min);

        menu.stream().map(dish -> 1).reduce(0, (a, b) -> a+ b);

    }
}
