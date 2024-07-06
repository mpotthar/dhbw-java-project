package com.example.services;

import java.util.HashSet;
import com.example.models.ElementModel;
import com.example.utility.Coordinate;

/**
 * This class is responsible for generating Sudoku puzzles.
 * It implements the ISudokuGeneratorService interface.
 */
public class SudokuGeneratorService implements ISudokuGeneratorService {

    private SeededRandom random;

    public SudokuGeneratorService() {
        random = new SeededRandom();
    }

    @Override
    public ElementModel[][] generate(int blanks) {
        ElementModel[][] matrix = new ElementModel[9][9];

        for (ElementModel[] column : matrix) {
            for (int i = 0; i < column.length; i++) {
                var element = new ElementModel();
                element.value = 0;
                column[i] = element;
            }
        }
        var succes = false;
        do {
            succes = fillMatrix(matrix, 0, 0);
        } while (!succes);

        var blankSpots = new HashSet<Coordinate<Integer, Integer>>();
        for (int i = 0; i < blanks; i++) {
            var coordinate = new Coordinate<>(random.nextInt(0, 9), random.nextInt(0, 9));
            var isSet = blankSpots.contains(coordinate);
            if (!isSet) {
                var m = coordinate.x;
                var n = coordinate.y;
                matrix[m][n].isHidden = true;
                blankSpots.add(coordinate);
            } else {
                i--;
            }
        }
        return matrix;
    }

    /**
     * Recursively fills the Sudoku matrix with valid numbers.
     *
     * @param matrix The Sudoku matrix to be filled.
     * @param row The current row index.
     * @param col The current column index.
     * @return True if the matrix is successfully filled, false otherwise.
     */
    private boolean fillMatrix(ElementModel[][] matrix, int row, int col) {
        if (row == 9) {
            return true; // Reached the end of the board
        }
        if (col == 9) {
            return fillMatrix(matrix, row + 1, 0); // Move to the next row
        }
        var numbers = random.ints(1, 10).distinct().limit(9).toArray(); // Generate numbers 1-9 in random order
        for (int number : numbers) {
            if (isValidPlacement(matrix, row, col, number)) {
                matrix[row][col].value = number;
                if (fillMatrix(matrix, row, col + 1)) {
                    return true;
                }
                matrix[row][col].value = 0; // Reset the cell if it doesn't lead to a solution
            }
        }
        return false; // Trigger backtracking
    }

    /**
     * Checks if a given number can be placed in the specified position of the Sudoku matrix.
     *
     * @param matrix The Sudoku matrix represented as a 2D array of ElementModel objects.
     * @param row The row index of the position to check.
     * @param col The column index of the position to check.
     * @param num The number to be placed in the position.
     * @return true if the number can be placed in the position, false otherwise.
     */
    private boolean isValidPlacement(ElementModel[][] matrix, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (matrix[row][i].value == num || matrix[i][col].value == num) {
                return false;
            }
        }
        // Check 3x3 sub-grid
        var startRow = row - row % 3;
        var startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[startRow + i][startCol + j].value == num) {
                    return false;
                }
            }
        }
        return true;
    }

}
