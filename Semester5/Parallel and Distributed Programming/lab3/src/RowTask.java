public class RowTask implements Runnable {

    private final Integer[][] answerMatrix;
    private final Integer start;
    private final Integer end;

    public RowTask(Integer[][] answerMatrix, Integer start, Integer end) {
        this.answerMatrix = answerMatrix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int m = Main.columns2;
        int i = start / m;
        int j = start % m;
        int iterations = end - start;
        for (int iteration = 0; iteration < iterations; iteration++) {
            answerMatrix[i][j] = CustomMatrix.getDotProductForCell(Main.matrix1, Main.matrix2, i, j);
            j++;
            if (j == m) {
                j = 0;
                i++;
            }
        }

    }
}
