import java.util.Arrays;

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
        for(int i = start; i<end;i++) {
            if(i> result.length)
                return;
            //for any power you can only add powers lower than the calculated one
            //such that the sum of the powers is the calculated power,
            //ie for a step where we want to calculate the new coefficient given by the multiplication of a power of r^t
            //where t<result.length-1 we have x^a * y^b where a+b=t and a,b<t
            //so for the coefficient (after multiplication of 2 polynomials) of r^4 of the result we have x^0*y^4+x^4*y^0+x^3*y^1+x^1*y^3+x^2*y^2
            for(int j=0;j<=i;j++)
            {
               if(j<firstPolynomial.length && (i-j) < secondPolynomial.length) {

                   result[i] += firstPolynomial[j] * secondPolynomial[i-j];
               }
            }
        }
    }
}
