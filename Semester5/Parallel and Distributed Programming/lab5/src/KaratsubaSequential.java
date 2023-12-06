import java.util.Arrays;

public class KaratsubaSequential {
    public static Integer[] karatsubaMultiply(Integer[] poly1, Integer[] poly2) {
        int n = Math.max(poly1.length, poly2.length);
        if (n == 1) {
            return new Integer[]{poly1[0] * poly2[0]};
        }

        Integer[] a = Arrays.copyOfRange(poly1, 0, n / 2);
        Integer[] b = Arrays.copyOfRange(poly1, n / 2, poly1.length);
        Integer[] c = Arrays.copyOfRange(poly2, 0, n / 2);
        Integer[] d = Arrays.copyOfRange(poly2, n / 2, poly2.length);


        Integer[] ac = karatsubaMultiply(a, c);
        Integer[] bd = karatsubaMultiply(b, d);
        Integer[] ab_cd = karatsubaMultiply(add(a, b), add(c, d));

        Integer[] result = new Integer[n * 2];
        Arrays.fill(result, 0);  // Initialize all elements to 0

        for (int i = 0; i < ac.length; i++) {
            result[i] += ac[i];
        }
        for (int i = 0; i < bd.length; i++) {
            result[i + n] += bd[i];
        }
        for (int i = 0; i < ab_cd.length; i++) {
            result[i + n / 2] += ab_cd[i] - (i < ac.length ? ac[i] : 0) - (i < bd.length ? bd[i] : 0);
        }
        return result;
    }

//    private Integer[] addPaddingToPolynomial(int desiredLength, Integer[] polynomial) {
//        Integer[] result = new Integer[desiredLength];
//        result
//    }

    public static Integer[] add(Integer[] poly1, Integer[] poly2) {
        int n = Math.max(poly1.length, poly2.length);
        Integer[] result = new Integer[n];
        Arrays.fill(result, 0);
        for (int i = 0; i < n; i++) {
            result[i] = (i < poly1.length ? poly1[i] : 0) + (i < poly2.length ? poly2[i] : 0);
        }
        return result;
    }
}
