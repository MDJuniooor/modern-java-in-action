package chapter5;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {
    public static void main(String[] args) {
        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = { 2, 3, 5, 7, 11, 13 };
        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // iterate를 이용한 피보나치
        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
                .limit(10)
                .forEach(t -> System.out.printf("(%d, %d)", t[0], t[1]));

        Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        IntStream.iterate(0, n -> n + 4).takeWhile(n -> n < 100).forEach(System.out::println);

        // generate Supplier가 필요하다.

        // Stream.generate를 이용한 임의의 double 스트림
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // Stream.generate을 이용한 요소 1을 갖는 스트림
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);

        IntStream.generate(new IntSupplier() {
            // 익명 클래스는 상태를 커스터마이징할 수 있어 부작용이 있을 수 있는 코드다.
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {

            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int nextValue = previous + current;
                previous = current;
                current = nextValue;
                return previous;
            }

        };
        IntStream.generate(fib)
                .limit(10)
                .forEach(System.out::println);

//        long uniqueWords = Files.lines(Paths.get("lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
//                .flatMap(line -> Arrays.stream(line.split(" ")))
//                .distinct()
//                .count();

//        System.out.println("There are " + uniqueWords + " unique words in data.txt");

        // 무한한 크기를 가진 스트림을 처리하고 있으므로 limit를 이용해서 명시적으로 스트림의 크기를 제한해야 한다.
        // 그렇지 않으면 최종 연산을 수행했을 때 아무 결과도 계산되지 않는다.
        // 마찬가지고 무한 스트림의 요소는 무한적으로 계산이 반복되므로 정렬하거나 리듀스할 수 없다.
    }
}
