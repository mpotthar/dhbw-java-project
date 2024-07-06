package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Sudoku API application.
 */
@SpringBootApplication
public class SudokuApiApplication {
    public static void main(String[] args) {
        var app = SudokuApiApplication.class;
        SpringApplication.run(app, args);
    }
}
