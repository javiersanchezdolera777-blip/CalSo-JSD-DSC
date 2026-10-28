package com.calso.robot.domain.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EnergyTest {

    @Test
    void testGivenEnergy10WhenCreatingEnergyThenStoresValue10() {
        Energy energy = Energy.of(10);
        
        // Comprobamos que getValue() devuelve el valor 10 según la especificación
        assertEquals(10, energy.getValue());
    }
    
    @Test
    void testGivenEnergyMinus1WhenCreatingEnergyThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Energy.of(-1));
    }

    @Test
    void testGivenEnergy0WhenCreatingEnergyThenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Energy.of(0));
    }

    @Test
    void testGivenTwoEnergyValues10WhenComparingThenTheyAreEqual() {
        Energy first = Energy.of(10);
        Energy second = Energy.of(10);
        assertEquals(first, second);
    }
}