import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

class KaratsubaTask extends RecursiveTask<Integer[]> {
    private final Integer[] poly1;
    private final Integer[] poly2;

    public KaratsubaTask(Integer[] poly1, Integer[] poly2) {
        this.poly1 = poly1;
        this.poly2 = poly2;
    }

    @Override
    protected Integer[] compute() {
        int n = Math.max(poly1.length, poly2.length);
        if (n == 1) {
            return new Integer[]{poly1[0] * poly2[0]};
        }

        Integer[] a = Arrays.copyOfRange(poly1, 0, n / 2);
        Integer[] b = Arrays.copyOfRange(poly1, n / 2, poly1.length);
        Integer[] c = Arrays.copyOfRange(poly2, 0, n / 2);
        Integer[] d = Arrays.copyOfRange(poly2, n / 2, poly2.length);

        KaratsubaTask acTask = new KaratsubaTask(a, c);
        KaratsubaTask bdTask = new KaratsubaTask(b, d);
        KaratsubaTask ab_cdTask = new KaratsubaTask(add(a, b), add(c, d));

        acTask.fork();
        bdTask.fork();
        ab_cdTask.fork();

        Integer[] ac = acTask.join();
        Integer[] bd = bdTask.join();
        Integer[] ab_cd = ab_cdTask.join();

        Integer[] result = new Integer[2 * n];
        Arrays.fill(result, 0);
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

    private Integer[] add(Integer[] poly1, Integer[] poly2) {
        int n = Math.max(poly1.length, poly2.length);
        Integer[] result = new Integer[n];
        Arrays.fill(result, 0);
        for (int i = 0; i < n; i++) {
            result[i] = (i < poly1.length ? poly1[i] : 0) + (i < poly2.length ? poly2[i] : 0);
        }
        return result;
    }
}
