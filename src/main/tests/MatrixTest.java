import com.example.slots.utill.Matrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testRandomMatrixValues() {
        int[][] matrix = Matrix.randomMatrixValues();

        assertNotNull(matrix);
        assertEquals(3, matrix.length);
        assertEquals(5, matrix[0].length);
    }

    @Test
    void testGetValues() {
        int[][] inputMatrix = {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3}
        };

        Matrix matrix = new Matrix(inputMatrix);
        int[][] resultMatrix = matrix.getValues();

        assertArrayEquals(inputMatrix, resultMatrix);
    }

    @Test
    void testSetValues() {
        int[][] inputMatrix = {
                {1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3}
        };

        Matrix matrix = new Matrix();
        matrix.setValues(inputMatrix);
        int[][] resultMatrix = matrix.getValues();

        assertArrayEquals(inputMatrix, resultMatrix);
    }

    @Test
    void testCheckLine4() {
        int[][] matrix = {
                {1, 0, 0, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0}
        };

        assertTrue(Matrix.checkLine4(matrix));
    }

    @Test
    void testCheckLine2() {
        int[][] matrix = {
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        assertTrue(Matrix.checkLine2(matrix));
    }

    @Test
    void testCheckLine9() {
        int[][] matrix = {
                {1, 0, 2, 0, 1},
                {0, 1, 0, 1, 0},
                {3, 0, 2, 0, 3}
        };

        assertFalse(Matrix.checkLine9(matrix));
    }

    @Test
    void testCheckLine6() {
        int[][] matrix = {
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1}
        };

        assertFalse(Matrix.checkLine6(matrix));
    }

    @Test
    void testCheckLine1() {
        int[][] matrix = {
                {2, 2, 2, 2, 2},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        assertTrue(Matrix.checkLine1(matrix));
    }

    @Test
    void testCheckLine7() {
        int[][] matrix = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {1, 1, 1, 1, 1}
        };

        assertFalse(Matrix.checkLine7(matrix));
    }

    @Test
    void testCheckLine8() {
        int[][] matrix = {
                {1, 0, 2, 0, 1},
                {0, 1, 0, 1, 0},
                {3, 0, 2, 0, 3}
        };

        assertFalse(Matrix.checkLine8(matrix));
    }

    @Test
    void testCheckLine3() {
        int[][] matrix = {
                {3, 3, 3, 3, 3},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        assertTrue(Matrix.checkLine3(matrix));
    }

    @Test
    void testCheckLine5() {
        int[][] matrix = {
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1}
        };

        assertTrue(Matrix.checkLine5(matrix));
    }

    @Test
    void testCheckLine10() {
        int[][] matrix = {
                {0, 1, 2, 3, 4},
                {5, 0, 1, 2, 3},
                {4, 5, 0, 1, 2}
        };

        assertFalse(Matrix.checkLine10(matrix));
    }
}