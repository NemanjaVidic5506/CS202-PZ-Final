package com.example.slots.utill;

public class Matrix {
    public static final double PROBABILITY = 0.1;
    final int[][] values = new int[3][5];
    /**
     * Sets the values of the current object using a 2D array of integers.
     * Copies the values from the provided array to the internal array of the object.
     *
     * @param values A 2D array of integers representing the values to be set.
     *               The dimensions of the array should match the internal array dimensions (3x5 in this case).
     */
    public void setValues(int[][] values) {
        for (int i = 0; i < 3; i++) {
            System.arraycopy(values[i], 0, this.values[i], 0, 5);
        }
    }
    /**
     * Retrieves a copy of the internal 2D array representing the values of the Matrix object.
     *
     * @return A cloned 2D array of integers representing the values of the Matrix.
     */
    public int[][] getValues() {
        return values.clone();
    }
    /**
     * Default constructor for the Matrix class.
     * Initializes the Matrix object with random values generated using the randomMatrixValues method.
     */

    public Matrix() {
        setValues(randomMatrixValues());
    }
    /**
     * Parameterized constructor for the Matrix class.
     * Initializes the Matrix object with the provided 2D array of values.
     *
     * @param values A 2D array of integers representing the values to be set for the Matrix.
     *               The dimensions of the array should match the internal array dimensions (3x5 in this case).
     */
    public Matrix(int[][] values) {
        setValues(values);
    }

    @FunctionalInterface
    public interface onLineValidation {
        void validate(int index);
    }
    /**
     * Calculates a multiplier based on the validation of specific lines in the matrix.
     * The method checks various line patterns in the matrix and updates the provided onLineValidation instance
     * for each validated line. The final multiplier is the sum of the weights associated with the validated lines.
     *
     * @param line An implementation of the onLineValidation interface to validate and track the validated lines.
     * @return The calculated multiplier based on the validated lines in the matrix.
     */
    public int getMultiplier(onLineValidation line) {
        int multiplier = 0;

        if (checkLine4(values)) {
            line.validate(0);
            multiplier += 4;
        }
        if (checkLine2(values)) {
            line.validate(1);
            multiplier += 2;
        }
        if (checkLine9(values)) {
            line.validate(2);
            multiplier += 9;
        }
        if (checkLine6(values)) {
            line.validate(3);
            multiplier += 6;
        }
        if (checkLine1(values)) {
            line.validate(4);
            multiplier += 1;
        }
        if (checkLine7(values)) {
            line.validate(5);
            multiplier += 7;
        }
        if (checkLine8(values)) {
            line.validate(6);
            multiplier += 8;
        }
        if (checkLine3(values)) {
            line.validate(7);
            multiplier += 3;
        }
        if (checkLine5(values)) {
            line.validate(8);
            multiplier += 5;
        }
        if (checkLine10(values)) {
            line.validate(9);
            multiplier += 10;
        }
        return multiplier;
    }
    /**
     * Generates a 3x5 matrix with random integer values and applies a fixing mechanism.
     * The fixing mechanism ensures that the generated matrix meets certain criteria.
     *
     * @return A 2D array of integers representing the generated matrix with fixed values.
     */
    public static int[][] randomMatrixValues() {
        final int[][] values = new int[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                values[i][j] = randomNumber();
            }
        }
        fixMatrix(values);
        return values;
    }

    private static void fixMatrix(final int[][] matrix) {
        if (Math.random() > PROBABILITY) {
            return;
        }

        final double probability = Math.random();

        if (probability < 0.1) {
            setLine10(matrix);
        } else if (probability < 0.2) {
            setLine5(matrix);
        } else if (probability < 0.3) {
            setLine3(matrix);
        } else if (probability < 0.4) {
            setLine8(matrix);
        } else if (probability < 0.5) {
            setLine7(matrix);
        } else if (probability < 0.6) {
            setLine1(matrix);
        } else if (probability < 0.7) {
            setLine6(matrix);
        } else if (probability < 0.8) {
            setLine9(matrix);
        } else if (probability < 0.9) {
            setLine2(matrix);
        } else {
            setLine4(matrix);
        }
    }

    private static int randomNumber() {
        return (int) (Math.random() * 4);
    }
    /**
     * Checks if a specific line pattern is satisfied in the given matrix.
     * The method compares the elements in the matrix against a reference value
     * to determine if they follow the expected pattern for the specified line.
     *
     * @param matrix The 2D array of integers representing the matrix to check.
     * @return true if the line pattern is satisfied, false otherwise.
     */
    public static boolean checkLine4(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            if (matrix[i][i] != matrix[0][0])
                return false;
            if (matrix[i][4 - i] != matrix[0][0])
                return false;
        }
        return true;
    }
    /**
     * Sets the values of a specific line in the given matrix to a reference value.
     * The method updates the elements in the matrix to match the reference value
     * for the specified line, ensuring it follows the expected pattern.
     *
     * @param matrix The 2D array of integers representing the matrix to modify.
     */

    //nadalje su iste metode samo za druge linije tako da ovaj docs opisuje i donje metode
    public static void setLine4(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            matrix[i][i] = matrix[0][0];
            matrix[i][4 - i] = matrix[0][0];
        }
    }

    public static boolean checkLine2(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            if (matrix[0][i] != matrix[0][0])
                return false;
        }
        return true;
    }

    public static void setLine2(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            matrix[0][i] = matrix[0][0];
        }
    }

    public static boolean checkLine9(final int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            if (matrix[0][i] != matrix[1][2])
                return false;
            if (matrix[2][4 - i] != matrix[1][2])
                return false;
        }
        return true;
    }

    public static void setLine9(final int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            matrix[0][i] = matrix[1][2];
            matrix[2][4 - i] = matrix[1][2];
        }
    }

    public static boolean checkLine6(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            if (matrix[2][i] != matrix[1][0])
                return false;
        }
        return matrix[1][4] == matrix[1][0];
    }

    public static void setLine6(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            matrix[2][i] = matrix[1][0];
        }
        matrix[1][4] = matrix[1][0];
    }

    public static boolean checkLine1(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            if (matrix[1][i] != matrix[1][0])
                return false;
        }
        return true;
    }

    public static void setLine1(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            matrix[1][i] = matrix[1][0];
        }
    }

    public static boolean checkLine7(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            if (matrix[0][i] != matrix[1][0])
                return false;
        }
        return matrix[1][4] == matrix[1][0];
    }

    public static void setLine7(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            matrix[0][i] = matrix[1][0];
        }
        matrix[1][4] = matrix[1][0];
    }

    public static boolean checkLine8(final int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            if (matrix[2][i] != matrix[1][2])
                return false;
            if (matrix[0][4 - i] != matrix[1][2])
                return false;
        }
        return true;
    }

    public static void setLine8(final int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            matrix[2][i] = matrix[1][2];
            matrix[0][4 - i] = matrix[1][2];
        }
    }

    public static boolean checkLine3(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            if (matrix[2][i] != matrix[2][0])
                return false;
        }
        return true;
    }

    public static void setLine3(final int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            matrix[2][i] = matrix[2][0];
        }
    }

    public static boolean checkLine5(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            if (matrix[2 - i][i] != matrix[2][0])
                return false;
            if (matrix[i][2 + i] != matrix[2][0])
                return false;
        }
        return true;
    }

    public static void setLine5(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            matrix[2 - i][i] = matrix[2][0];
            matrix[i][2 + i] = matrix[2][0];
        }
    }

    public static boolean checkLine10(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            if (matrix[1][i + 1] != matrix[0][4])
                return false;
        }
        return matrix[2][0] == matrix[0][4];
    }

    public static void setLine10(final int[][] matrix) {
        for (int i = 0; i < 3; i++) {
            matrix[1][i + 1] = matrix[0][4];
        }
        matrix[2][0] = matrix[0][4];
    }
}