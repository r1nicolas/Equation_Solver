package rnicolas.equation_solver;

import java.util.Arrays;

/**
 * Created by rnicolas on 03/11/15.
 */
public class PolynomialSolver {
    private int degree;
    private double[] coef;

    PolynomialSolver(int degree, double[] coefficients) {
        this.degree = degree;
        this.coef = coefficients;

        while (this.coef.length > 0 && this.coef[this.degree] == 0) {
            this.coef = Arrays.copyOf(this.coef, this.degree);
            this.degree--;
        }
    }

    double[]    solveFirst() {
        double[] result = new double[1];

        result[0] = -coef[0] / coef[1];
        return (result);
    }

    double[]    solveSecond() {
        double delta;
        double[] result;

        delta = coef[1] * coef[1] - 4 * coef[2] * coef[0];
        if (delta < 0) {
            result = new double[0];
        } else if (delta == 0) {
            result = new double[1];
            result[0] = -coef[1] / (2 * coef[2]);
        } else {
            result = new double[2];
            result[0] = (-coef[1] - Math.sqrt(delta)) / (2 * coef[2]);
            result[1] = (-coef[1] + Math.sqrt(delta)) / (2 * coef[2]);
        }
        return (result);
    }

    double[]    solveThird() {
        double[] result;
        double p, q, delta, u, v;

        p = (3 * coef[3] * coef[1] - coef[2] * coef[2]) / (3 * coef[3] * coef[3]);
        q = (2 * coef[2] * coef[2] * coef[2] - 9 * coef[3] * coef[2] * coef[1] + 27 * coef[3] * coef[3] * coef[0]) / (27 * coef[3] * coef[3] * coef[3]);

        delta = ((q * q) + ((4 * p * p * p) / 27));

        if (delta > 0) {
            u = (-q - Math.sqrt(delta)) / 2;
            v = (-q + Math.sqrt(delta)) / 2;
            if (u < 0)
                u = -Math.pow(-u, 1.0 / 3);
            else
                u = Math.pow(u, 1.0 / 3);
            if (v < 0)
                v = -Math.pow(-v, 1.0 / 3);
            else
                v = Math.pow(v, 1.0 / 3);
            result = new double[1];
            result[0] =  u + v - coef[2] / (3 * coef[3]);
        } else if (delta == 0) {
            if (p != 0) {
                result = new double[2];
                result[0] = (3 * q / p) - coef[2] / (3 * coef[3]);
                result[1] = -(3 * q) / (2 * p) - coef[2] / (3 * coef[3]);
            } else {
                result = new double[1];
                result[0] = - coef[2] / (3 * coef[3]);
            }
        } else {
            result = new double[3];
            Complex uc = Complex.pow(new Complex(-q / 2, Math.sqrt(-delta) / 2), 1.0 / 3);
            Complex vc = Complex.pow(new Complex(-q / 2, -Math.sqrt(-delta) / 2), 1.0 / 3);
            Complex j = new Complex(-1.0 / 2, Math.sqrt(3) / 2);
            Complex j2 = Complex.mult(j, j);
            result[0] = Complex.add(uc, vc).getR() - coef[2] / (3 * coef[3]);
            result[1] = Complex.add(Complex.mult(uc, j), Complex.mult(vc, j2)).getR() - coef[2] / (3 * coef[3]);
            result[2] = Complex.add(Complex.mult(uc, j2), Complex.mult(vc, j)).getR() - coef[2] / (3 * coef[3]);
        }
        return (result);
    }

    boolean testPair() {
        if (degree % 2 == 1)
            return (false);
        for (int i = 1;i < degree; i = i + 2) {
            if (coef[1] != 0)
                return (false);
        }
        return (true);
    }

    double[] solvePair() {
        double[] tmpCoef = new double[degree / 2 + 1], tmpResult, result;
        int tmpDegree = degree / 2;
        int i, j = 0;

        for (i = 0;i <= tmpDegree;i++) {
            tmpCoef[i] = coef[i * 2];
        }

        PolynomialSolver tmpSolver = new PolynomialSolver(tmpDegree, tmpCoef);
        tmpResult = tmpSolver.solve();

        for (i = 0;i < tmpResult.length;i++) {
            if (tmpResult[i] > 0)
                j = j + 2;
            else if (tmpResult[i] == 0)
                j++;
        }
        result = new double[j];
        j = 0;
        for (i = 0;i < tmpResult.length;i++) {
            if (tmpResult[i] > 0) {
                result[j] = Math.sqrt(tmpResult[i]);
                result[j + 1] = -Math.sqrt(tmpResult[i]);
                j = j + 2;
            } else if (tmpResult[i] == 0) {
                result[j] = 0;
                j++;
            }
        }
        return (result);
    }


    boolean testSimpleRoot() {
        int i;

        for (i = 1;i < degree;i++) {
            if (coef[i] != 0)
                return (false);
        }
        return (true);
    }

    double[] solveSimpleRoot() {
        double tmp;
        double[] result;

        tmp = -coef[0] / coef[degree];
        if (degree % 2 == 1) {
            result = new double[1];
            if (tmp >= 0)
                result[0] = Math.pow(tmp, 1.0 / degree);
            else
                result[0] = -Math.pow(-tmp, 1.0 / degree);
        }
        else {
            if (tmp >= 0) {
                result = new double[2];
                result[0] = Math.pow(tmp, 1.0 / degree);
                result[1] = - result[0];
            }
            else
                result = new double[0];
        }
        return (result);
    }

    double[] solveFourth() {
        double[] result, tmpCoef, tmpResult;
        double p, q, r, u, v, w;
        PolynomialSolver tmpSolver;
        int length, i;

        p = (8 * coef[4] * coef[2] - 3 * coef[3] * coef[3]) / (8 * coef[4] * coef[4]);
        q = (8 * coef[4] * coef[4] * coef[1] - 4 * coef[4] * coef[3] * coef[2] + coef[3] * coef[3] * coef[3]) / (8 * coef[4] * coef[4] * coef[4]);
        r = (256 * coef[4] * coef[4] * coef[4] * coef[0] - 64 * coef[4] * coef[4] * coef[3] * coef[1] + 16 * coef[4] * coef[3] * coef[3] * coef[2] - 3 * coef[3] * coef[3] * coef[3] * coef[3]) / (256 * coef[4] * coef[4] * coef[4] * coef[4]);


        if (q != 0) {
            tmpCoef = new double[]{-q * q, 0, p * p - 4 * r, 0, 2 * p, 0, 1};
            tmpSolver = new PolynomialSolver(6, tmpCoef);
            tmpResult = tmpSolver.solve();
            u = tmpResult[0];
            v = (p * u + u * u * u - q) / (2 * u);
            w = (p * u + u * u * u + q) / (2 * u);
            tmpCoef = new double[]{v, u, 1};
            tmpSolver = new PolynomialSolver(2, tmpCoef);
            result = tmpSolver.solve();
            tmpCoef = new double[]{w, -u, 1};
            tmpSolver = new PolynomialSolver(2, tmpCoef);
            tmpResult = tmpSolver.solve();
            length = result.length;
            result = Arrays.copyOf(result, length + tmpResult.length);
            for (i = 0; i < tmpResult.length; i++)
                result[i + length] = tmpResult[i];
        } else {
            tmpCoef = new double[]{r, q, p, 0, 1};
            tmpSolver = new PolynomialSolver(4, tmpCoef);
            result = tmpSolver.solve();
        }
        for (i = 0;i < result.length;i++)
            result[i] -= (coef[3] / (4 * coef[4]));

        return (result);
    }

    double[] derivative(){
        double[] result = new double[degree];
        int i;

        for (i = 1;i <= degree;i++){
            result[i - 1] = i * coef[i];
        }
        return (result);
    }

    private static double[] removeDuplicates(double[] arr) {
        int i, j;
        double[] result = new double[arr.length];

        Arrays.sort(arr);

        j = 0;
        result[0] = arr[0];
        for (i = 1;i < arr.length;i++) {
            if (arr[i] != result[j]) {
                j++;
                result[j] = arr[i];
            }
        }
        result = Arrays.copyOf(result, j + 1);

        return (result);
    }

    double[] solve() {
        double[] result, tmp;
        PolynomialSolver help;

        if (degree <= 0)
            result = new double[0];
        else if (degree == 1)
            result = solveFirst();
        else if (coef[0] == 0) {
            help = new PolynomialSolver(degree - 1, Arrays.copyOfRange(coef, 1, degree + 1));
            tmp = help.solve();
            result = Arrays.copyOf(tmp, tmp.length + 1);
            result[tmp.length] = 0;
        } else if (testSimpleRoot())
            result = solveSimpleRoot();
        else if (testPair())
            result = solvePair();
        else if (degree == 2)
            result = solveSecond();
        else if (degree == 3)
            result = solveThird();
        else if (degree == 4)
            result = solveFourth();
        else
            result = new double[0];

        return (removeDuplicates(result));
    }
}

