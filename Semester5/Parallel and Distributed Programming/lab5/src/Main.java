import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Integer[] poly1 = new Integer[]{5, 0, 10, 6};
        Integer[] poly2 = new Integer[]{1, 2, 4};

        SquaredNSequential squaredNSequential = new SquaredNSequential();
        SquaredNParallel squaredNParallel = new SquaredNParallel();

        Integer[] resultSquaredNSequential = new Integer[poly2.length+poly1.length-1];
        Integer[] resultSquaredNParallel = new Integer[poly2.length+poly1.length-1];
        Integer[] resultKaratsubaSequential = new Integer[poly2.length+poly1.length-1];
        Integer[] resultKaratsubaParallel = new Integer[poly2.length+poly1.length-1];

        Arrays.fill(resultSquaredNSequential, 0);
        Arrays.fill(resultSquaredNParallel, 0);
        Arrays.fill(resultKaratsubaSequential, 0);
        Arrays.fill(resultKaratsubaParallel, 0);

        long startTime, endTime, duration;
        double durationInSeconds;

        startTime = System.nanoTime();
        squaredNSequential.multiplyPolynomials(poly1,poly2,resultSquaredNSequential);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        durationInSeconds = duration / 1_000_000.0;
        System.out.println("SquaredNSequential method : resultPolynomial = " + printPoly(resultSquaredNSequential) + "duration of execution : " + durationInSeconds + "milliseconds");

        startTime = System.nanoTime();
        squaredNParallel.multiplyPolynomials(poly1,poly2, resultSquaredNParallel);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        durationInSeconds = duration / 1_000_000.0;
        System.out.println("SquaredNParallel method : resultPolynomial = " + printPoly(resultSquaredNParallel) + "duration of execution : " + durationInSeconds + "milliseconds");

        //padding of the 2 polynomials
        int n = Math.max(poly1.length, poly2.length);
        int poly1InitialLength = poly1.length;
        int poly2InitialLength = poly2.length;
        poly1 =Arrays.copyOf(poly1, n);
        poly2 = Arrays.copyOf(poly2, n);
        Arrays.fill(poly1, poly1InitialLength, n, 0);
        Arrays.fill(poly2, poly2InitialLength, n, 0);

        startTime = System.nanoTime();
        resultKaratsubaSequential = KaratsubaSequential.karatsubaMultiply(poly1,poly2);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        durationInSeconds = duration / 1_000_000.0;
        System.out.println("KaratsubaSequential method : resultPolynomial = " + printPoly(resultKaratsubaSequential) + "duration of execution : " + durationInSeconds + "milliseconds");

        startTime = System.nanoTime();
        resultKaratsubaParallel = new ForkJoinPool().invoke(new KaratsubaTask(poly1,poly2));
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        durationInSeconds = duration / 1_000_000.0;
        System.out.println("KaratsubaParallel method : resultPolynomial = " + printPoly(resultKaratsubaParallel) + "duration of execution : " + durationInSeconds + "milliseconds");

    }

    public static String printPoly(Integer[] polynomial) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < polynomial.length; i++) {
            stringBuilder.append(polynomial[i]);
            if (i != 0) {
                stringBuilder.append("x^").append(i);
            }
            if (i != polynomial.length - 1) {
                stringBuilder.append(" + ");
            }
        }
        return stringBuilder.toString();
    }
}
