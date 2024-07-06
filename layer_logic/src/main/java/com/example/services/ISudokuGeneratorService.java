package com.example.services;

import com.example.models.ElementModel;

/**
 * The ISudokuGeneratorService interface provides methods for generating Sudoku puzzles.
 */
public interface ISudokuGeneratorService {

    /**
     * Generates a Sudoku puzzle with the specified number of blank cells.
     *
     * @param blanks The number of blank cells in the generated puzzle.
     * @return A 2D array of ElementModel representing the generated Sudoku puzzle.
     */
    ElementModel[][] generate(int blanks);

}