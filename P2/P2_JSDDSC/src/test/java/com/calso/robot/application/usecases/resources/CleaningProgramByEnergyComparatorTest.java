package com.calso.robot.application.usecases.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.calso.robot.domain.model.CleaningProgram;
import com.calso.robot.domain.valueobjects.Energy;

class CleaningProgramByEnergyComparatorTest {

    private final CleaningProgramByEnergyComparator comparator = new CleaningProgramByEnergyComparator();

    @Test
    void testGivenProgramsWithDifferentEnergyWhenComparingThenOrdersByEnergyAscending() {
        CleaningProgram p1 = new CleaningProgram("A", "Baja", Energy.of(10), new HashMap<>());
        CleaningProgram p2 = new CleaningProgram("B", "Alta", Energy.of(50), new HashMap<>());
        
        // p1 tiene menos energía que p2, debe devolver un número negativo
        assertTrue(comparator.compare(p1, p2) < 0);
    }

    @Test
    void testGivenProgramsWithSameEnergyWhenComparingThenOrdersByIdAscending() {
        CleaningProgram p1 = new CleaningProgram("P01", "Limpieza A", Energy.of(20), new HashMap<>());
        CleaningProgram p2 = new CleaningProgram("P02", "Limpieza B", Energy.of(20), new HashMap<>());
        
        // Empate en energía (20), pero "P01" va antes que "P02", debe ser negativo
        assertTrue(comparator.compare(p1, p2) < 0);
    }

    @Test
    void testGivenProgramsWithSameEnergyAndIdWhenComparingThenReturnsZero() {
        CleaningProgram p1 = new CleaningProgram("P01", "Limpieza 1", Energy.of(20), new HashMap<>());
        CleaningProgram p2 = new CleaningProgram("P01", "Limpieza 2", Energy.of(20), new HashMap<>());
        
        // Empate total en los criterios de ordenación
        assertEquals(0, comparator.compare(p1, p2));
    }
}