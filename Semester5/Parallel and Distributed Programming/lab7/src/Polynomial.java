import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Polynomial implements Serializable {
    private List<Integer> coefficients;
    private int degree;

    public Polynomial(int degree) {
        this.degree = degree;

        // generate coefficients
        this.coefficients = new ArrayList<>();
        Random randomGenerator = new Random();
        for(int i = 0; i < degree; i++) {
            coefficients.add(randomGenerator.nextInt(10));
        }
        // the free term
        coefficients.add(randomGenerator.nextInt(10));
    }

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public int getDegree() {
        return degree;
    }

    // A polynomial with 0 values in the array of coefficients
    public static Polynomial EmptyPolynomial(int degree) {
        // generate coefficients
        List<Integer> zeroCoefficients = new ArrayList<>();
        for(int i = 0; i <= degree; i++) {
            zeroCoefficients.add(0);
        }
        return new Polynomial(zeroCoefficients);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (coefficients.get(degree) != 0) {
            if(coefficients.get(degree) != 1) {
                builder.append(coefficients.get(degree)).append("x^").append(degree);
            } else {
                builder.append("x^").append(degree);
            }
        }

        for (var i = degree - 1; i > 0; --i) {
            if (coefficients.get(i) != 0) {
                if(coefficients.get(i) != 1) {
                    builder.append(" + ").append(coefficients.get(i)).append("x^").append(i);
                } else {
                    builder.append(" + ").append("x^").append(i);
                }
            }
        }
        if (coefficients.get(0) != 0)
            builder.append(" + ").append(coefficients.get(0));
        return builder.toString();
    }

    public static Polynomial add(Polynomial p1, Polynomial p2){
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        //Add the 2 polynomials
        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) + p2.getCoefficients().get(i));
        }

        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2){
        int minDegree = Math.min(p1.getDegree(), p2.getDegree());
        int maxDegree = Math.max(p1.getDegree(), p2.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        for (int i = 0; i <= minDegree; i++) {
            coefficients.add(p1.getCoefficients().get(i) - p2.getCoefficients().get(i));
        }

        if (minDegree != maxDegree) {
            if (maxDegree == p1.getDegree()) {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p1.getCoefficients().get(i));
                }
            } else {
                for (int i = minDegree + 1; i <= maxDegree; i++) {
                    coefficients.add(p2.getCoefficients().get(i));
                }
            }
        }

        int i = coefficients.size() - 1;
        while (coefficients.get(i) == 0 && i > 0) {
            coefficients.remove(i);
            i--;
        }

        return new Polynomial(coefficients);
    }

    public static Polynomial addZeros(Polynomial p, int offset) {
        List<Integer> coefficients = IntStream.range(0, offset).mapToObj(i -> 0).collect(Collectors.toList());
        coefficients.addAll(p.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial computeCoefficientsOfResult(Polynomial p1, Polynomial p2, int startCoefficient, int endCoefficient) {
        Polynomial result = EmptyPolynomial(2 * p1.getDegree());

        for (int i = startCoefficient; i <= endCoefficient; i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                result.getCoefficients().set(i + j, result.getCoefficients().get(i + j) + p1.getCoefficients().get(i) * p2.getCoefficients().get(j));
            }
        }

        return result;
    }

    public static Polynomial computeSumOfPolynomials(ArrayList<Object> polynomials) {
        int degree = ((Polynomial) polynomials.get(0)).getDegree();
        Polynomial result = EmptyPolynomial(degree);

        for (Object polynomial: polynomials) {
            result = Polynomial.add(result, (Polynomial) polynomial);
        }

        return result;
    }

    public static Polynomial regularSequential(Polynomial p1, Polynomial p2){
        Polynomial result = EmptyPolynomial(p1.getDegree() + p2.getDegree());

        for (int i = 0; i < p1.getCoefficients().size(); i++) {
            for (int j = 0; j < p2.getCoefficients().size(); j++) {
                int index = i + j;
                int rez = p1.getCoefficients().get(i) * p2.getCoefficients().get(j) + result.getCoefficients().get(index);
                result.getCoefficients().set(index, rez);
            }
        }

        return  result;
    }

    public static Polynomial KaratsubaSequential(Polynomial p1, Polynomial p2){
        if (p1.getDegree() < 2 || p2.getDegree() < 2)
            return regularSequential(p1, p2);

        int m = Math.max(p1.getDegree(), p2.getDegree()) / 2;

        Polynomial low1 = new Polynomial(p1.getCoefficients().subList(0, m));
        Polynomial high1 = new Polynomial(p1.getCoefficients().subList(m, p1.getDegree() + 1));
        Polynomial low2 = new Polynomial(p2.getCoefficients().subList(0, m));
        Polynomial high2 = new Polynomial(p2.getCoefficients().subList(m, p2.getDegree() + 1));

        Polynomial z0 = KaratsubaSequential(low1, low2);
        Polynomial z1 = KaratsubaSequential(Polynomial.add(low1, high1), Polynomial.add(low2, high2));
        Polynomial z2 = KaratsubaSequential(high1, high2);

        Polynomial rez1 = Polynomial.addZeros(z2, 2 * m);
        Polynomial rez2 = Polynomial.addZeros(Polynomial.subtract(Polynomial.subtract(z1, z2), z0), m);

        return Polynomial.add(Polynomial.add(rez1, rez2), z0);
    }
}