import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SquaredNParallel {

    private static final int THREAD_NUMBER = 4;

    public void multiplyPolynomials(Integer[] firstPolynomial, Integer[] secondPolynomial, Integer[] resultPolynomial) throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_NUMBER);
        int nrOfTasks = resultPolynomial.length / THREAD_NUMBER;
        if (nrOfTasks == 0) {
            nrOfTasks = 1;
        }
        int end;
        for(int i=0;i<resultPolynomial.length;i+=nrOfTasks)
        {
            end = i+nrOfTasks;
            Task task = new Task(i, end, firstPolynomial, secondPolynomial, resultPolynomial);
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);
    }


}
