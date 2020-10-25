package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

public abstract class LA {

    private final static int MAXNBVALUESDISPLAYED = 11;
    private final static double N_ZERO = 10.E-15;

    public static class LAException extends Exception{
        LAException(String message) {super (message) ; }
    }
    public static double [][] power(double[][] A, int n) throws LAException{
        if(A.length != A[0].length) throw new LAException ("matrix not square") ;
        if (n < 0) return power(inverse(A), -n) ;
        if (n == 0) return identity(A.length) ;
        if (n == 1) return A;
        double[][] T = A ;
        for (int i = 0; i < n ; i++) // if n >= 2
            T = multiply(A,T);
            return  T;
    }
    public static double [][] inverse(double A[][]) throws LAException{
        if(A.length != A[0].length) throw new LAException ("matrix not square") ;
        int nbRows = A.length ;
        int nbColumns = A[0].length ;
        double T[][] = joinMatrices(A,identity(nbRows));
        if (!eliminate(T)) throw new LAException ("singular matrix");
        eliminateUp(T);
        return subMatrix(T,0,nbColumns,nbRows - 1, 2 * nbColumns - 1);
    }

    public static double[][] identity(int dimension) {
        double[][] I = new double[dimension][dimension];
            for (int index = 0; index < dimension; index ++ )
                I[index][index] = 1; // Java initalize new variable to zero
        return I ;
    }

    public static double [][] joinMatrices (double [][] A, double [][] B) throws LAException {
        if (A.length != B.length) throw  new LAException ("Wrong dimensions") ;
        int nbRows = A.length ;
        int nbColumnsA = A[0].length ;
        int nbColumnsB = B[0].length ;
        double [][] joinedMatrix = new double[nbRows][nbColumnsA+nbColumnsB] ;
        int row, column ;
        for (row = 0; row < nbRows ; row++) {
            for (column = 0; column < nbColumnsA ; column++)
                joinedMatrix[row][column] = A[row][column];
                for (column = 0; column < nbColumnsB ; column++)
                   joinedMatrix[row][nbColumnsA + column] = B[row][column] ;
            }
        return joinedMatrix ;
    }

    public static double[][] multiply(double[][] A, double[][] B)  throws LAException {
        if (A[0].length != B.length) throw new LAException("matrix dimensions do not allows matrix multiplication") ;
        int nbRows = A.length ;
        int nbColumns = B[0].length ;
        double[][] C = new double[nbRows][nbColumns];
        int row, column, inRowColumn ;
        for (row = 0; row < nbRows; row ++)
            for (column = 0; column < nbColumns; column++)
                //C[row][Column] = 0; initialization done by JAVA
                for (inRowColumn = 0; inRowColumn < B.length; inRowColumn ++)
                    C[row][column] += A[row][inRowColumn] * B[inRowColumn][column] ;

                return C;
    }

    public static double[][] add(double[][] A, double[][] B) { return termByTerm(A, B, LA::add); }
    public static double[][] add(double[][] A, double b) { return termByTerm(A, b, LA::add); }
    public static double[][] add(double a, double[][] B) { return termByTerm(B, a, LA::add); }

    public static double[][] substract(double[][] A, double[][] B) { return termByTerm(A, B, LA::substract); }
    public static double[][] substract(double[][] A, double b) { return termByTerm(A, b, LA::substract); }

    public static double[][] multiply(double[][] A, double b) { return termByTerm(A, b, LA::multiply); }
    public static double[][] multiply(double a, double[][] B) { return termByTerm(B, a, LA::multiply); }

    private static double add(double a, double b) { return roundToZero(a + b); }
    private static double substract(double a, double b) { return roundToZero(a - b); }
    private static double multiply(double a, double b) { return roundToZero(a * b); }

    private static double[][] termByTerm(double A[][], double B[][], BiFunction<Double, Double, Double> myOperator) {
        if ((A.length != B.length) || (A[0].length != B[0].length)) {
            System.out.println("add: wrong dimensions");
            return null;
        }
        int nbRows = A.length ;
        int nbColumns = A[0].length ;
        double C[][] = new double [nbRows][nbColumns] ;
        int row, column ;

        for (row = 0; row < nbRows ; row++)
            for ( column = 0; column < nbColumns ; column++) {
                C[row][column] = myOperator.apply(A[row][column], B[row][column]) ;
            }
        return C;
    }
    private static double[][] termByTerm(double A[][], double b, BiFunction<Double, Double, Double> myOperator) {
        int nbRows = A.length;
        int nbColumns = A[0].length;
        double C[][] = new double[nbRows][nbColumns];
        int row, column;

        for (row = 0; row < nbRows; row++)
            for (column = 0; column < nbColumns; column++) {
                C[row][column] = myOperator.apply(A[row][column], b);
            }
        return C;
    }
    public static double[][] subMatrix(double A[][],
                                       int firstRow, // top
                                       int firstColumn, // left
                                       int lastRow, // bottom
                                       int lastColumn) // right
    {
        int nbRows = A.length ;
        int nbColumns = A[0].length ;
        if (!(firstRow >= 0 && firstRow <= lastRow && lastRow < nbRows &&
                firstColumn >= 0 && firstColumn <= lastColumn && lastColumn < nbColumns)){
            System.out.println("subMatrice: wrong indices");
            return null ;
        }
        double [][] subMatrix = new double[lastRow - firstRow + 1][lastColumn - firstColumn + 1];
        int row, column ;
        for (row = 0; row <= lastRow ; row++)
            for (column = firstColumn; column <= lastColumn ; column++)
                subMatrix[row - firstRow][column - firstColumn] = A[row][column] ;
            return subMatrix;
        }


    public static void elimateGj(double[][] A) {
        eliminate(A);
        eliminateUp(A);
    }

    private static void eliminateUp(double[][]U) {
        int nbRows = U.length;
        int nbColumns = U[0].length;
        int row, column;
        for(row = nbRows - 1; row >= 0; row--)
            for(column = row; column < nbColumns; column++){
                if(U[row][column] == 0.) continue;
                makeFirstOne(U, row, column);
                eliminateColumnUp(U, row, column);
                break;
            }
    }

    private static void eliminateColumnUp(double[][] U, int row, int column) {
        int nbColumns = U[0].length;
        double pivot;
        int tRow, tColumn;
        for (tRow = 0; tRow < row; tRow++) {
            if (U[tRow][column] == 0.) continue;
            pivot = U[tRow][column]; // / U[row][column] == 1 division non nécessaire
            U[tRow][column] = 0.;
            for (tColumn = column + 1; tColumn < nbColumns; tColumn++) {
                U[tRow][tColumn] = roundToZero(U[tRow][tColumn] - pivot * U[row][tColumn]);
            }
        }
    }

    private static void makeFirstOne(double[][] U, int row, int column) {
        int nbColumns = U[0].length;
        for (int tColumn = column + 1; tColumn < nbColumns; tColumn++) {
            if(U[row][tColumn] == 0.) continue;
            U[row][tColumn] /= U[row][column];
        }
        U[row][column] = 1.;
    }

    public static double[] solveRegularSystem(double[][] A) {
        int nb_equations = A.length;
        int nb_unknowns = A[0].length -1;
        if (nb_equations != nb_unknowns) {
            System.out.println("solveRegularSystem: nb equations != nbunknowns !!!");
            return null;
        }
        if(!eliminate(A)) {
            System.out.println("solveRegularSystem: singular system !!!");
            return null;
        }
        return substituteBack(A);
    }

    private static double[] substituteBack(double[][] U) {
        int nbRows = U.length;
        int nbColumns = U[0].length;
        double[] solution = new double[nbRows];
        int row, column;
        for (row = nbRows - 1; row >= 0; row--) {
            solution[row] = U[row][nbColumns - 1];
            for (column = nbColumns - 2; column > row; column--)
                solution[row] -= U[row][column] * solution[column];
            solution[row] /= U[row][row];
        }
        return solution;
    }

    public static boolean eliminate(double[][] A) {
        int nbRows = A.length;
        int nbColumns = A[0].length;
        int row = 0, column = 0;
        boolean regular = true;
        while (row < nbRows && column < nbColumns) {
            if (setBestFirstRow(A, row, column))
                eliminateColumnDown(A, row++, column++);
            else {
                column++;
                regular = false;
            }
        }
        return regular;
    }

    private static boolean setBestFirstRow(double[][] A, int row, int column) {
        int nbRows = A.length;
        double bestValue = Math.abs(A[row][column]);
        int bestRow = row;
        double value;
        for (int tRow = row + 1; tRow < nbRows; tRow++) {
            value = Math.abs(A[tRow][column]);
            if (bestValue < value) {
                bestRow = tRow;
                bestValue = value;
            }
        }
        swapRows(A, row, bestRow);
        return bestValue > 0.0;
    }

    public static void swapRows(double[][] A, int row1, int row2) {
        if (row1 == row2) return;
        double[] temp = A[row1]; //Only works with Java
        A[row1] = A[row2];
        A[row2] = temp;
    }

    private static double roundToZero(double value) {
        if (Math.abs(value) > N_ZERO) return value;
        if (value == 0.0) return 0.0;
        System.out.println("roundToZero : " + value);
        return 0.0;
    }

    public static void eliminateColumnDown(double[][] A, int row, int column) {
        int nbRows = A.length;
        int nbColumns = A[0].length;
        double pivot;
        int tRow, tColumn;
        for (tRow = row + 1; tRow < nbRows; tRow++) {
            if (A[tRow][column] == 0.) continue;
            pivot = A[tRow][column] / A[row][column];
            A[tRow][column] = 0.;
            for (tColumn = column + 1; tColumn < nbColumns; tColumn++) {
                A[tRow][tColumn] = roundToZero(A[tRow][tColumn] - pivot * A[row][tColumn]);
            }
        }
    }

    public static String toString(double[] vector) {
        if (vector == null) return "toString: empty vector !!!";
        int nbValues = vector.length;
        StringBuilder stringBuilder = new StringBuilder("\n[");
        if (nbValues <= MAXNBVALUESDISPLAYED) {
            for (double value : vector)
                stringBuilder.append(String.format(" %.3f ", value));
        } else {
            stringBuilder.append(String.format(" %.3f ", vector[0]));
            stringBuilder.append(String.format(" %.3f ", vector[1]));
            stringBuilder.append(String.format(" %.3f ", vector[2]));
            stringBuilder.append("  ...  ").append(nbValues - 6).append(" values suppressed ...");
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 3]));
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 2]));
            stringBuilder.append(String.format(" %.3f ", vector[nbValues - 1]));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String toString(double[][] matrix) {
        if (matrix == null) return "toString: empty vector !!!";
        int nbRows = matrix.length;
        StringBuilder stringBuilder = new StringBuilder();
        if (nbRows <= MAXNBVALUESDISPLAYED) {
            for (double[] row : matrix)
                stringBuilder.append(toString(row));
        } else {
            stringBuilder.append(toString(matrix[0]));
            stringBuilder.append(toString(matrix[1]));
            stringBuilder.append(toString(matrix[2]));
            stringBuilder.append("\n[ .....................");
            stringBuilder.append("\n[     ").append(nbRows - 6).append(" rows suppressed");
            stringBuilder.append("\n[ .....................");
            stringBuilder.append(toString(matrix[nbRows - 3]));
            stringBuilder.append(toString(matrix[nbRows - 2]));
            stringBuilder.append(toString(matrix[nbRows - 1]));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static void showArray(double[] vector) {
        System.out.println(toString(vector));
    }

    public static void showVector(double[] vector) {
        showArray(vector);
    }

    public static void showArray(double[][] matrix) {
        System.out.println(toString(matrix));
    }

    public static void showMatrix(double[][] matrix) {
        showArray(matrix);
    }

    public static double[][] randomMatrix(int nbRows, int nbColumns) {
        Random generator = new Random();
        double[][] matrix = new double[nbRows][nbColumns];
        int row, column;
        for (row = 0; row < nbRows; row++)
            for (column = 0; column < nbColumns; column++)
                matrix[row][column] = generator.nextDouble() * 2.0 - 1.0;
        return matrix;
    }

    public static double[] copyOf(double[] vector) {
        return Arrays.copyOf(vector, vector.length);
    }

    public static double[][] copyOf(double[][] matrix) {
        double[][] copy = new double[matrix.length][];
        for (int row = 0; row < matrix.length; row++)
            copy[row] = copyOf(matrix[row]);
        return copy;
    }



}