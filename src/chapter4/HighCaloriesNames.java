package chapter4;

import java.util.*;

import static chapter4.Dish.menu;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class HighCaloriesNames {
    public static void main(String[] args) {
        // 칼로리가 낮은 메뉴 리스트 출력하는 코드
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish dish: menu){
            if(dish.getCalories() < 400){
                lowCaloricDishes.add(dish);
            }
        };
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(),o2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        for(Dish dish: lowCaloricDishes){
            lowCaloricDishesName.add(dish.getName());
        }

        // 최신 자바 코드
        List<String> lowCaloricDishesName2 =
                menu.stream()
                    //.parallelStream() 병렬로 실행
                    .filter(d -> d.getCalories() < 400)
                    .sorted(comparing(Dish::getCalories))
                    .map(Dish::getName)
                    .collect(toList());

        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream().collect(groupingBy(Dish::getType));

    }
}
