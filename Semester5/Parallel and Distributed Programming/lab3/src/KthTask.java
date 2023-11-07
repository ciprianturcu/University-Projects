public class KthTask implements Runnable {

    private final Integer[][] answerMatrix;
    private final Integer start;
    private final Integer step;

    public KthTask(Integer[][] answerMatrix, Integer start, Integer step) {
        this.answerMatrix = answerMatrix;
        this.start = start;
        this.step = step;
    }

    @Override
    public void run() {
        int n = Main.rows1;
        int m = Main.columns2;
        int i = 0;
        int j = start;
        while (true) {
            int rowsPassed = j / m;
            i += rowsPassed;
            j -= rowsPassed * m;
            if (i >= n)
                break;
            answerMatrix[i][j] = CustomMatrix.getDotProductForCell(Main.matrix1, Main.matrix2, i, j);
            j+=step;
        }

    }
}
