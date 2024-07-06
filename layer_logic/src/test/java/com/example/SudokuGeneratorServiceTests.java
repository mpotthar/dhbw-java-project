package com.example;

import static org.junit.Assert.assertArrayEquals;

import org.assertj.core.util.Arrays;
import org.junit.Test;

import com.example.models.ElementModel;
import com.example.services.ISudokuGeneratorService;
import com.example.services.SudokuGeneratorService;

import junit.framework.TestCase;

/**
 * This class contains unit tests.
 */
public class SudokuGeneratorServiceTests extends TestCase {

    private ISudokuGeneratorService sut;

    /**
     * Sets up the test environment before each test case is executed.
     * This method is called before each test case to initialize any necessary objects or resources.
     * Overrides the setUp method from the parent class.
     *
     * @throws Exception if an error occurs during setup.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sut = new SudokuGeneratorService();
    }

    /**
        * Cleans up the test environment after each test case.
        * Sets the `sut` (System Under Test) variable to null and calls the superclass's `tearDown` method.
        *
        * @throws Exception if an error occurs during the tear down process.
        */
    @Override
    protected void tearDown() throws Exception {
        sut = null;
        super.tearDown();
    }

    /**
     * Test case to verify that the generated matrix is not null and all elements are not null.
     */
    @Test
    public void test_for_null() {
        var matrix = sut.generate(40);
        assertNotNull(matrix);
        for (ElementModel[] columns : matrix) {
            for (ElementModel element : columns) {
                assertNotNull(element);
            }
        }
    }

    /**
     * Test case to verify the column count of the generated matrix.
     */
    @Test
    public void test_column_count() {
        var expected = 9;
        var matrix = sut.generate(40);
        var actual = matrix.length;
        assertEquals(expected, actual);
    }

    /**
     * Test case to verify the row count of the generated matrix.
     */
    @Test
    public void test_row_count() {
        var expected = 9;
        var matrix = sut.generate(40);
        for (ElementModel[] elementModels : matrix) {
            var actual = elementModels.length;
            assertEquals(expected, actual);
        }
    }

    /**
     * Test case to verify that all elements in the generated matrix have allowed symbols.
     */
    @Test
    public void test_allowed_symbols() {
        var expected = Arrays.asList(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
        var matrix = sut.generate(40);
        for (ElementModel[] columns : matrix) {
            for (ElementModel element : columns) {
                var actual = element.value;
                var isContained = expected.contains(actual);
                assertTrue(isContained);
            }
        }
    }

    /**
     * Test case to verify the columns of the generated matrix.
     */
    @Test
    public void test_columns() {
        var expected = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        var matrix = sut.generate(40);
        for (int n = 0; n < matrix.length; n++) {
            var actual = new int[9];
            for (int m = 0; m < matrix.length; m++) {
                var element = matrix[m][n];
                var index = element.value - 1;
                actual[index]++;
            }
            assertArrayEquals(expected, actual);
        }
    }

    /**
     * Test case to verify the rows of the generated matrix.
     */
    @Test
    public void test_rows() {
        var expected = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        var matrix = sut.generate(40);
        for (int m = 0; m < matrix.length; m++) {
            var actual = new int[9];
            for (int n = 0; n < matrix.length; n++) {
                var element = matrix[m][n];
                var index = element.value - 1;
                actual[index]++;
            }
            assertArrayEquals(expected, actual);
        }
    }

    /**
     * Test case to verify the sub-grids of the generated matrix.
     */
    @Test
    public void test_sub_grids() {

        var expected = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        var matrix = sut.generate(40);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                var actual = new int[9];
                var mStart = x * 3;
                var nStart = x * 3;
                for (int m = mStart; m < mStart + 3; m++) {
                    for (int n = nStart; n < nStart + 3; n++) {
                        var element = matrix[m][n];
                        var index = element.value - 1;
                        actual[index]++;
                    }
                }
                assertArrayEquals(expected, actual);
            }
        }
    }

    /**
     * Test case to verify the count of blank elements in the generated matrix.
     */
    @Test
    public void test_blank_count() {
        var expected = 40;
        var actual = 0;
        var matrix = sut.generate(expected);
        for (ElementModel[] columns : matrix) {
            for (ElementModel element : columns) {
                if (element.isHidden) {
                    actual++;
                }
            }
        }
        assertEquals(expected, actual);
    }

}
