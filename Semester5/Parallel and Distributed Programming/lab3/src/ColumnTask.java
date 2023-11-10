public class ColumnTask implements Runnable {

    private final Integer[][] answerMatrix;

    private final Integer start;
    private final Integer end;

    public ColumnTask(Integer[][] answerMatrix, Integer start, Integer end) {
        this.answerMatrix = answerMatrix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int n = Main.rows1;
        int i = start % n;
        int j = start / n;
        int iterations = end - start;
        for (int iteration = 0; iteration < iterations; ++iteration) {
            answerMatrix[i][j] = CustomMatrix.getDotProductForCell(Main.matrix1, Main.matrix2, i, j);
            ++i;
            if (i == n) {
                i = 0;
                ++j;
            }
        }
    }
}
