package chapter3;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static chapter3.Color.GREEN;

public class Lambdas {

    static Map<String, Function<Integer, Apple>> map = new HashMap<>();

    static {
        map.put("apple", Apple::new);
        map.put("banana", Apple::new);
        map.put("kiwi", Apple::new);
        map.put("grape", Apple::new);
    }

    public static Apple giveMeFruit(String fruit, Integer weight){
        return map.get(fruit.toLowerCase())
                .apply(weight);
    }

    public static void main(String[] args) {
        Supplier<Apple> c1 = Apple::new;
        c1 = () -> new Apple();
        Apple a1 = c1.get();

        Function<Integer, Apple> c2 = Apple::new;
        c2 = (weight) -> new Apple(weight);
        Apple a2 = c2.apply(100);

        List<Integer> weights = Arrays.asList(7, 3, 4 , 10);
        List<Apple> apples = map(weights, Apple::new);

        BiFunction<Integer, Color, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(110, GREEN);


    }
    public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for(Integer i: list){
            result.add(f.apply(i));
        }
        return result;
    }
}
