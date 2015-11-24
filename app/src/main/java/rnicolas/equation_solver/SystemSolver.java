package rnicolas.equation_solver;

public class SystemSolver {
    private int size;
    private double[][] matrix;

    public SystemSolver(int size, double[][] matrix){
        this.size = size;
        this.matrix = matrix;
    }

    private void triangularize() throws Exception {
        int i, j;
        for (i = 0;i < size;i++){
            for (j = i; j < size && matrix[j][i] == 0; j++) {}
            if (j == size)
                throw new Exception();
            else
                swap(i, j);
            for (j = i + 1; j < size; j++) {
                if (matrix[j][i] != 0)
                    reduce (i, j);
            }
        }
    }

    private void swap(int n, int m)
    {
        double[] tmp;

        tmp = matrix[n];
        matrix[n] = matrix[m];
        matrix[m] = tmp;
    }

    private void reduce (int n, int m)
    {
        double f = -matrix[n][n] / matrix[m][n];
        int i;

        for (i = 0;i <= size;i++){
            matrix[m][i] = f * matrix[m][i] + matrix[n][i];
        }
    }

    public double[] solve() throws Exception {
        double aux;
        double[] sol = new double[size];
        int i, j;
        triangularize();

        for (i = size - 1; i >= 0; i--)
        {
            aux = matrix[i][size];
            for (j = i + 1; j < size;j++)
                aux -= matrix[i][j] * sol[j];
            sol[i] = aux / matrix[i][i];
        }
        return (sol);
    }
    @Override
    public String toString() {
        String str = "";
        int i,j;
        for (i = 0; i < size; i++) {
            for (j = 0; j <= size; j++) {
                if (j > 0)
                    str += " ";
                str += matrix[i][j];
            }
            str += "\n";
        }
        return str;
    }
}

