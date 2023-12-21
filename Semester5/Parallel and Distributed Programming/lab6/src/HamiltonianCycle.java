import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class HamiltonianCycle {

    public static void searchCycle(Map<Integer, Set<Integer>> graph, List<Integer> path) throws InterruptedException {
        int currentVertex = path.get(path.size() - 1);
        //System.out.println(path);
        if (path.size() == graph.size() && graph.get(currentVertex).contains(path.get(0))) {
            System.out.println(path);
            return;
        }

        if(path.size() == graph.size())
            return;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (int neighbour : graph.get(currentVertex)) {
            if (!path.contains(neighbour)) {
                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(neighbour);
                executor.execute(
                        () -> {
                            try {
                                searchCycle(graph, newPath);
                            }
                            catch (Exception e ){
                                throw new RuntimeException(e.getMessage());
                            }
                        }
                );
            }
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }



    public static Map<Integer, Set<Integer>> getGraphExample1() {
        Map<Integer, Set<Integer>> graph1 = new HashMap<>();
        graph1.put(0, new HashSet<>(Arrays.asList(1, 2)));
        graph1.put(1, new HashSet<>(Arrays.asList(2, 3)));
        graph1.put(2, new HashSet<>(Arrays.asList(3, 4)));
        graph1.put(3, new HashSet<>(Arrays.asList(4, 5)));
        graph1.put(4, new HashSet<>(Arrays.asList(5, 6)));
        graph1.put(5, new HashSet<>(Arrays.asList(6, 7)));
        graph1.put(6, new HashSet<>(Arrays.asList(7, 8)));
        graph1.put(7, new HashSet<>(Arrays.asList(8, 9)));
        graph1.put(8, new HashSet<>(Arrays.asList(9, 0)));
        graph1.put(9, new HashSet<>(Arrays.asList(0, 1)));
        return graph1;
    }

    public static Map<Integer, Set<Integer>> getGraphExample2() {
        Map<Integer, Set<Integer>> graph2 = new HashMap<>();
        graph2.put(0, new HashSet<>(Arrays.asList(1, 2, 9)));
        graph2.put(1, new HashSet<>(Arrays.asList(2, 3, 0)));
        graph2.put(2, new HashSet<>(Arrays.asList(3, 4, 1)));
        graph2.put(3, new HashSet<>(Arrays.asList(4, 5, 2)));
        graph2.put(4, new HashSet<>(Arrays.asList(5, 6, 3)));
        graph2.put(5, new HashSet<>(Arrays.asList(6, 7, 4)));
        graph2.put(6, new HashSet<>(Arrays.asList(7, 8, 5)));
        graph2.put(7, new HashSet<>(Arrays.asList(8, 9, 6)));
        graph2.put(8, new HashSet<>(Arrays.asList(9, 0, 7)));
        graph2.put(9, new HashSet<>(Arrays.asList(0, 1, 8)));
        return graph2;
    }

    public static Map<Integer, Set<Integer>> getGraphExample3() {
        Map<Integer, Set<Integer>> graph3 = new HashMap<>();
        graph3.put(0, new HashSet<>(Arrays.asList(1, 9)));
        graph3.put(1, new HashSet<>(Arrays.asList(2)));
        graph3.put(2, new HashSet<>(Arrays.asList(3)));
        graph3.put(3, new HashSet<>(Arrays.asList(4)));
        graph3.put(4, new HashSet<>(Arrays.asList(5)));
        graph3.put(5, new HashSet<>(Arrays.asList(6)));
        graph3.put(6, new HashSet<>(Arrays.asList(7)));
        graph3.put(7, new HashSet<>(Arrays.asList(8)));
        graph3.put(8, new HashSet<>(Arrays.asList(9)));
        graph3.put(9, new HashSet<>(Arrays.asList(0, 1)));
        return graph3;
    }
}
