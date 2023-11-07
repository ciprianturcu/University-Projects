import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Integer rows1, columns1AndRows2, columns2;
    public static CustomMatrix matrix1, matrix2;
    private static Integer functionType;
    private static Integer scanType;
    private static Integer taskNumber;
    private static Integer threadNumber;


    public static void main(String[] args) throws InterruptedException {
        getInput();
        CustomMatrix trueDotProduct = CustomMatrix.trueDotProduct(matrix1, matrix2);
        CustomMatrix dotProduct;
        if (functionType == 0)
            dotProduct = dotProductByTask4Thread();
        else dotProduct = dotProductByThreadPool();
        if (trueDotProduct.equals(dotProduct)) {
            System.out.println("dot product successful");
            System.out.println("Matrix1 :");
            matrix1.print();
            System.out.println("Matrix2 :");
            matrix2.print();
            System.out.println("dot product matrix :");
            dotProduct.print();
        } else {
            System.out.println("dot product failed");
            System.out.println("Matrix1 :");
            matrix1.print();
            System.out.println("Matrix2 :");
            matrix2.print();
            System.out.println("dot product matrix:");
            dotProduct.print();
            System.out.println("true dot product matrix");
            trueDotProduct.print();
        }
    }

    public static void getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("rows1: ");
        rows1 = scanner.nextInt();
        System.out.print("columns1AndRows2: ");
        columns1AndRows2 = scanner.nextInt();
        System.out.print("c: ");
        columns2 = scanner.nextInt();
        System.out.print("function type (0-thread4task ; 1-threadPool): ");
        functionType = scanner.nextInt();
        if (functionType == 1) {
            System.out.print("threads number: ");
            threadNumber = scanner.nextInt();
        }
        System.out.print("scan type (0-row, 1-column, 2-k-th element): ");
        scanType = scanner.nextInt();
        System.out.print("task number: ");
        taskNumber = scanner.nextInt();
        initializeMatrices();
    }

    private static void initializeMatrices() {
        matrix1 = new CustomMatrix(rows1, columns1AndRows2);
        matrix2 = new CustomMatrix(columns1AndRows2, columns2);
    }

    private static CustomMatrix dotProductByTask4Thread() throws InterruptedException {
        Integer[][] answerMatrix = new Integer[rows1][columns2];
        List<Thread> tasks = new ArrayList<>();
        int iterationsPerTask = rows1 * columns2 / taskNumber;
        for (int i = 0; i < taskNumber; i++) {
            int start = i * iterationsPerTask;
            int end = Math.min((i + 1) * iterationsPerTask, rows1 * columns2);
            if (scanType == 0)
                tasks.add(new Thread(new RowTask(answerMatrix, start, end)));
            else if (scanType == 1)
                tasks.add(new Thread(new ColumnTask(answerMatrix, start, end)));
            else tasks.add(new Thread(new KthTask(answerMatrix, start, taskNumber)));
        }

        tasks.forEach(Thread::start);
        for (Thread thread : tasks) {
            thread.join();
        }
        return new CustomMatrix(answerMatrix);
    }

    private static CustomMatrix dotProductByThreadPool() throws InterruptedException {
        Integer[][] answerMatrix = new Integer[rows1][columns2];
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNumber);
        List<Runnable> tasks = new ArrayList<>();
        int iterationsPerTask = rows1 * columns2 / taskNumber;
        for (int i = 0; i < taskNumber; i++) {
            int start = i * iterationsPerTask;
            int end = Math.min((i + 1) * iterationsPerTask, rows1 * columns2);
            if (functionType == 0)
                tasks.add(new Thread(new RowTask(answerMatrix, start, end)));
            else if (functionType == 1)
                tasks.add(new Thread(new ColumnTask(answerMatrix, start, end)));
            else tasks.add(new Thread(new KthTask(answerMatrix, start, taskNumber)));
        }
        tasks.forEach(threadPoolExecutor::execute);
        threadPoolExecutor.shutdown();
        return new CustomMatrix(answerMatrix);
    }
}
