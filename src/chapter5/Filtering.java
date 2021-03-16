package chapter5;

import chapter4.Dish;

import java.util.Arrays;
import java.util.List;

import static chapter4.Dish.Type.MEAT;
import static chapter4.Dish.menu;
import static java.util.stream.Collectors.toList;

public class Filtering {
    public static void main(String[] args) {

        // 5.1.1. Predicate로 필터링
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());

        // 5.1.2. 고유 요소 필터링
        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 5.2.1 프레디케이트를 이용한 슬라이싱
        // 칼로리 값을 기준으로 리스트를 오름차순 정렬되어 있음
        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());

        List<Dish> sliceMenu1 = specialMenu.stream()
                // 320보다 크거나 같은 요리가 나왔을 때 반복 작업을 중단
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        List<Dish> sliceMenu2 = specialMenu.stream()
                // Predicate가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
                // 무한한 남은 요소를 가진 무한 스트림에서도 동작한다.
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());

        // 5.2.2 스트림 축소
        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());

        // 5.2.3 요소 건너뛰기 (limit 연산과 상호보완)
        List<Dish> dishes2 = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .collect(toList());

        // Quiz 처음 등장하는 두 고기 요리를 필터링
        List<Dish> meatDishes = specialMenu.stream()
                .filter(dish -> MEAT.equals(dish.getType()))
                .limit(2)
                .collect(toList());

    }
}
