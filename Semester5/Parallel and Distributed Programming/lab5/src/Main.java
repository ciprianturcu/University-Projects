public class Main {

    public static void main(String[] args) {
        SquaredNSequential squaredNSequential = new SquaredNSequential(new Integer[]{5, 0, 10, 6}, new Integer[]{1, 2, 4});
        Integer[] result = squaredNSequential.multiplyPolynomials();
        System.out.println(SquaredNSequential.printPoly(result));
    }
}
