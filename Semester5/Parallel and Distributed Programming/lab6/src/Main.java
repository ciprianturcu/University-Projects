import java.util.*;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        var graph = getGraphByUserInput();
        for(Integer node : graph.keySet()) {
            System.out.println("solutions for node " + node);
            List<Integer> path = new ArrayList<>();
            path.add(node);
            var startTime = System.nanoTime();
            HamiltonianCycle.searchCycle(graph, path);
            var endTime = System.nanoTime();
            System.out.println("Time: " + ((double) endTime - (double) startTime) / 1_000_000_000.0 + "s");
        }
    }

    private static void printMenu() {
        System.out.println("1 - graph1");
        System.out.println("2 - graph2");
        System.out.println("3 - graph3");
    }

    private static Map<Integer, Set<Integer>> getGraphByUserInput(){
        printMenu();
        Scanner scanner = new Scanner(System.in);
        System.out.print("option: ");
        int option = scanner.nextInt();
        return switch (option) {
            case 1 -> HamiltonianCycle.getGraphExample1();
            case 2 -> HamiltonianCycle.getGraphExample2();
            case 3 -> HamiltonianCycle.getGraphExample3();
            default -> {
                System.out.print("Not an option");
                yield getGraphByUserInput();
            }
        };
    }
}
