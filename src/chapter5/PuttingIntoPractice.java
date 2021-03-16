package chapter5;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // query1: 2011년에 일어난 모든 트랜잭션을 찾아 값을오름차순으로 정리
        transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        // 질의 2: 거래자가 근무하는 모든 고유 도시는?
        transactions.stream()
                    .map(Transaction::getTrader)
                    .map(Trader::getCity)
                    .distinct()
                    .collect(toList());

        // 질의 3: Cambridge의 모든 거래자를 찾아 이름으로 정렬.
        transactions.stream()
                    .map(Transaction::getTrader)
                    .filter(trader -> trader.getCity().equals("Cambridge"))
                    .distinct()
                    .sorted(comparing(Trader::getName))
                    .collect(toList());

        // 질의 4: 알파벳 순으로 정렬된 모든 거래자의 이름 문자열을 반환
        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);

        // 질의 5: Milan에 거주하는 거래자가 있는가?
        boolean milanBased = transactions.stream()
                                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        // 질의 6: Cambridge에 사는 거래자의 모든 거래내역 출력.
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 질의 7: 모든 거래에서 최고값은 얼마인가?
        int highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
        System.out.println(highestValue);

        // 가장 작은 값을 가진 거래 탐색
        Optional<Transaction> smallestTransaction = transactions.stream()
                .min(comparing(Transaction::getValue));
        // 거래가 없을 때 기본 문자열을 사용할 수 있도록 발견된 거래가 있으면 문자열로 바꾸는 꼼수를 사용함(예, the Stream is empty)
        System.out.println(smallestTransaction.map(String::valueOf).orElse("No transactions found"));

    }
}
