import java.util.Arrays;

public class SquaredNSequential {
    private Integer[] firstPolynomial;
    private Integer[] secondPolynomial;

    public SquaredNSequential(Integer[] firstPolynomial, Integer[] secondPolynomial) {
        this.firstPolynomial = firstPolynomial;
        this.secondPolynomial = secondPolynomial;
    }

    public Integer[] multiplyPolynomials() {
        Integer[] result = new Integer[firstPolynomial.length + secondPolynomial.length - 1];
        Arrays.fill(result, 0);
        for (int indexFirst = 0; indexFirst < firstPolynomial.length; indexFirst++) {
            for (int indexSecond = 0; indexSecond < secondPolynomial.length; indexSecond++) {
                result[indexFirst + indexSecond] += firstPolynomial[indexFirst] * secondPolynomial[indexSecond];
            }
        }
        return result;
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

    @Override
    public String toString() {
        return "SquaredNSequential method : " +
                "firstPolynomial = " + printPoly(firstPolynomial) +
                ", secondPolynomial=" + printPoly(secondPolynomial) +
                '}';
    }
}
