package chapter5;

import chapter4.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static chapter4.Dish.menu;
import static java.util.stream.Collectors.toList;

public class Mapping {
    public static void main(String[] args) {
        // 특정 객체에서 특정 데이터를 선택하는 작업
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);

        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);

        List<Integer> dishes = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println(dishes);

        // 5.3.2 스트림 평면화
        List<String[]> platwords = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());
        System.out.println(platwords); // [ [H,e,l,o], [W,o,r,l,d] ]

        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfWords = Arrays.stream(arrayOfWords);

        // map, Arrays.stream 활용
        List<Stream<String>> w = words.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(toList());
        System.out.println();

        // flatMap 사용
        List<String> uniqueCharacters =
                words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)// 생성된 스트림을 하나의 스트림으로 평면화
                .distinct()
                .collect(toList());
        System.out.println(uniqueCharacters);

        // quiz1
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> squares = numbers.stream()
                .map(number -> number * number)
                .collect(toList());
        System.out.println(squares);

        // quiz2
        List<Integer> n1 = Arrays.asList(1,2,3);
        List<Integer> n2 = Arrays.asList(3,4);
        List<int[]> pairs = n1.stream()
                .flatMap(i -> n2.stream()
                                .map(j -> new int[]{i,j}))
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
        System.out.println();
        // quiz3
        List<int[]> pairs2 = n1.stream()
                .flatMap(i -> n2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i,j}))
                .collect(toList());
        pairs2.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));

    }
}
