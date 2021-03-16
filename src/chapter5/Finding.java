package chapter5;

import chapter4.Dish;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static chapter4.Dish.menu;

public class Finding {
    public static void main(String[] args) {
        // 5.4.1 프레디케이트가 적어도 한 요소와 일치하는지 확인
        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        // 5.4.2 프레디케이트가 모든 요소와 일치하는지 검사
        boolean isHealthy = menu.stream()
                                .allMatch(dish -> dish.getCalories() < 1000);

        // noneMatch allMatch와 반대의 연산
        // anyMatch, allMatch, noneMatch 는 스트림 쇼트서킷 기법 &&,||와 같은 연산을 활용
        isHealthy = menu.stream()
                .noneMatch(dish -> dish.getCalories() >= 1000);

        // 5.4.3 요소 검색
        Optional<Dish> dish =
                menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish2 -> System.out.println(dish2.getName()));

        // 5.4.4 첫 번째 요소 찾기
        List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                            .map(n -> n * n)
                            .filter(n -> n % 3 == 0)
                            .findFirst();

    }
}
