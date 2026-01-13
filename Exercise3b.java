import java.util.*;
import java.util.stream.*;

public class Exercise3b {
    public static void main(String[] args) {

        // Generate list of 1,000,000 integers.
        List<Integer> list = IntStream.rangeClosed(1, 1_000_000)
                                      .boxed()
                                      .toList();

        // Sequential stream.
        long start = System.currentTimeMillis();
        long seqSum = list.stream().mapToLong(Integer::longValue).sum();
        long seqTime = System.currentTimeMillis() - start;

        // Parallel stream.
        start = System.currentTimeMillis();
        long parSum = list.parallelStream().mapToLong(Integer::longValue).sum();
        long parTime = System.currentTimeMillis() - start;

        // Execution times.
        System.out.println("Sequential sum: " + seqSum + " | Time: " + seqTime + " ms");
        System.out.println("Parallel sum:   " + parSum + " | Time: " + parTime + " ms");
    }
}
