import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class CustomMatrix {
    private Integer[][] matrix;
    private Integer rows, columns;

    public CustomMatrix(Integer[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    public CustomMatrix(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new Integer[rows][columns];
        Random random = new Random();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                matrix[i][j] = random.nextInt(10);
    }

    public static CustomMatrix trueDotProduct(CustomMatrix m1, CustomMatrix m2) {
        Integer[][] productMatrix = new Integer[m1.rows][m2.columns];
        for (int i = 0; i < m1.rows; i++)
            for (int j = 0; j < m2.columns; j++)
                productMatrix[i][j] = getDotProductForCell(m1, m2, i, j);
        return new CustomMatrix(productMatrix);
    }

    public int getCell(int i, int j) {
        return matrix[i][j];
    }

    public static int getDotProductForCell(CustomMatrix firstMatrix, CustomMatrix secondMatrix, int lineFirst, int columnSecond) {
        int dotProduct = 0;
        for (int i = 0; i < firstMatrix.columns; i++)
            dotProduct += firstMatrix.getCell(lineFirst, i) + secondMatrix.getCell(i, columnSecond);
        return dotProduct;
    }

    public void print() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomMatrix objectCustomMatrix))
            return false;
        if (!rows.equals(objectCustomMatrix.rows) || !columns.equals(objectCustomMatrix.columns))
            return false;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (!matrix[i][j].equals(objectCustomMatrix.matrix[i][j]))
                    return false;
        return true;

    }

}
