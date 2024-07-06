package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.ISudokuGeneratorService;
import com.example.services.SudokuGeneratorService;
import com.google.gson.Gson;

@RestController
public class SudokuApiController implements ISudokuApiController {

    private ISudokuGeneratorService generator;
    private Gson serializer;

    /**
     * Constructs a new SudokuApiController with the specified Gson serializer.
     *
     * @param gson the Gson serializer to be used
     */
    @Autowired
    public SudokuApiController(Gson gson) {
        generator = new SudokuGeneratorService();
        this.serializer = gson;
    }

    /**
     * Generates a Sudoku puzzle and returns it as a JSON string.
     *
     * @return the generated Sudoku puzzle as a JSON string
     */
    @Override
    @GetMapping(value = "/generate", produces = "application/json")
    public String generate() {
        var content = generator.generate(40);
        return serializer.toJson(content);
    }

}
