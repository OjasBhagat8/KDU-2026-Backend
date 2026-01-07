import java.util.concurrent.*;

public class Exercise3a {
    public static void main(String[] args) throws Exception {
        int N = 10;

        // ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // returns a sum from 1 - N
        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int i = 1; i <= N; i++) {
                sum += i;
            }
            return sum;
        };

        // getting a future by submitting sumTask to executor for execution.
        Future<Integer> future = executor.submit(sumTask);

        System.out.println("Submitted task. Doing other work on main thread...");

        //  Retrieve result from future.
        int result = future.get();

        System.out.println("Result = " + result);

        //  Shutdown executor
        executor.shutdown();

        System.out.println("Executor shut down.");
    }
}
