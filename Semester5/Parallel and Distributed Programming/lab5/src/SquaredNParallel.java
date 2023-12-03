import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SquaredNParallel {

    private static final int THREAD_NUMBER = 4;
    private Integer[] firstPolynomial;
    private Integer[] secondPolynomial;
    private Integer[] resultPolynomial;

    public SquaredNParallel(Integer[] firstPolynomial, Integer[] secondPolynomial) {
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;
        this.resultPolynomial = new Integer[firstPolynomial.length + secondPolynomial.length - 1];
        Arrays.fill(resultPolynomial, 0);
    }


    public void multiplyPolynomials() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_NUMBER);
        int nrOfTasks = resultPolynomial.length / THREAD_NUMBER;
        if (nrOfTasks == 0) {
            nrOfTasks = 1;
        }
        int end;
        for(int i=0;i<resultPolynomial.length;i++)
        {
            end = i+nrOfTasks;
            Task task = new Task()
        }
    }
}
