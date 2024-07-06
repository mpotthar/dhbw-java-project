package com.example.services;

import java.util.Random;

/**
 * A subclass of the `Random` class that initializes the seed using the current system time.
 */
public class SeededRandom extends Random {
    /**
     * Constructs a new `SeededRandom` object with the seed initialized using the current system time.
     */
    public SeededRandom() {
        super(System.currentTimeMillis());
    }
}
