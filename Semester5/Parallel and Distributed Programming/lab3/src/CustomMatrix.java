import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class CustomMatrix {
    private Integer[][] matrix;
    private Integer rows, columns;

    public CustomMatrix(Integer[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.matrix = matrix;
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
        if (!(o instanceof CustomMatrix objectCustomMatrix)) {
            System.out.println("primu if");
            return false;
        }
        if (!rows.equals(objectCustomMatrix.rows) || !columns.equals(objectCustomMatrix.columns)) {
            System.out.println("al doilea if");
            return false;
        }
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (!(Objects.equals(matrix[i][j], objectCustomMatrix.matrix[i][j]))) {
                    System.out.println("trei if");
                    System.out.println(this.matrix[i][j] + " " + objectCustomMatrix.matrix[i][j]);
                    return false;
                }
        return true;

    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            System.out.println("primu if");
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            System.out.println("al doilea if");
//            return false;
//        }
//        CustomMatrix that = (CustomMatrix) o;
//        if (Arrays.deepEquals(matrix, that.matrix) && Objects.equals(rows, that.rows) && Objects.equals(columns, that.columns)) {
//            System.out.println("trei if");
//            return true;
//        }
//        System.out.println("afaara if");
//        return false;
//    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }
}
