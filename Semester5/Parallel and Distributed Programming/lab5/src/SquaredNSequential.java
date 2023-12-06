import java.util.Arrays;

public class SquaredNSequential {

    public void multiplyPolynomials(Integer[] firstPolynomial, Integer[] secondPolynomial, Integer[] resultPolynomial) {
        for (int indexFirst = 0; indexFirst < firstPolynomial.length; indexFirst++) {
            for (int indexSecond = 0; indexSecond < secondPolynomial.length; indexSecond++) {
                resultPolynomial[indexFirst + indexSecond] += firstPolynomial[indexFirst] * secondPolynomial[indexSecond];
            }
        }
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
