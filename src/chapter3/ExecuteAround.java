package chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecuteAround {
    private static final String FILE = ExecuteAround.class.getResource("./data.txt").getFile();
    /*
    as-is file read
    public static String processFile() throws IOException {
        try(BufferedReader br = new BufferedReader((new FileReader("data.txt")))){
            return br.readLine();
        }
    }
     */
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }

    public static void main(String[] args) throws IOException {
        // read one line
        String result = processFile((BufferedReader br) -> br.readLine());

        // read two line
        String result2 = processFile((BufferedReader br) -> br.readLine() + br.readLine());

        List<Apple> a = new ArrayList<>();
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }
}
