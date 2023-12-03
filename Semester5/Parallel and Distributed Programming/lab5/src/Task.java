public class Task implements Runnable{

    private final int start;
    private final int end;
    private final Integer[] firstPolynomial;
    private final Integer[] secondPolynomial;
    private final Integer[] result;

    public Task(int start, int end, Integer[] firstPolynomial, Integer[] secondPolynomial, Integer[] result) {
        this.start = start;
        this.end = end;
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;
        this.result = result;
    }

    @Override
    public void run() {

    }
}
