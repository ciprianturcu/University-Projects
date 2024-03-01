import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class ParallelPrimeFinder {
    public static void main(String[] args) {
        int N = 100; // Define your N
        List<Integer> primesUpToRootN = getPrimesUpToRootN(N); // Assume this method exists

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<Integer>>> futures = new ArrayList<>();

        // Split range and submit tasks
        for (int i = (int)Math.sqrt(N) + 1; i <= N; i++) {
            int finalI = i;
            futures.add(executor.submit(() -> checkPrime(finalI, primesUpToRootN)));
        }

        // Collect and process results
        List<Integer> primes = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            try {
                primes.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown(); // Shut down the executor

        // Primes now contains all prime numbers up to N
    }

    private static List<Integer> checkPrime(int number, List<Integer> primesUpToRootN) {
        List<Integer> primeList = new ArrayList<>();
        if (isPrime(number, primesUpToRootN)) {
            primeList.add(number);
        }
        return primeList;
    }

    private static boolean isPrime(int number, List<Integer> primesUpToRootN) {
        for (int prime : primesUpToRootN) {
            if (number % prime == 0) {
                return false;
            }
        }
        return true;
    }

    // Method to get primes up to root N (implementation not shown)
    private static List<Integer> getPrimesUpToRootN(int N) {
        // Implement the logic to find primes up to √N
        return new ArrayList<>();
    }
}
