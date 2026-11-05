package com.calso.robot.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.calso.robot.domain.valueobjects.Energy;

class CleaningProgramTest {

    @Test
    void testGivenValidDataWhenCreatingProgramThenStoresPropertiesCorrectly() {
        Map<CleaningProduct, Integer> reqs = new HashMap<>();
        reqs.put(CleaningProduct.WATER, 2);
        Energy energy = Energy.of(10);
        
        CleaningProgram program = new CleaningProgram("P01", "Limpieza Diaria", energy, reqs);
        
        assertEquals("P01", program.getId());
        assertEquals("Limpieza Diaria", program.getName());
        assertEquals(energy, program.getEnergy());
        assertEquals(2, program.getRequirements().get(CleaningProduct.WATER));
    }

    @Test
    void testGivenNullIdWhenCreatingProgramThenThrowsIllegalArgumentException() {
        Map<CleaningProduct, Integer> reqs = new HashMap<>();
        Energy energy = Energy.of(10);
        
        assertThrows(IllegalArgumentException.class, 
            () -> new CleaningProgram(null, "Limpieza Diaria", energy, reqs));
    }

    @Test
    void testGivenBlankIdWhenCreatingProgramThenThrowsIllegalArgumentException() {
        Map<CleaningProduct, Integer> reqs = new HashMap<>();
        Energy energy = Energy.of(10);
        
        // La especificación dice que el ID no puede ser una cadena blanca (espacios)
        assertThrows(IllegalArgumentException.class, 
            () -> new CleaningProgram("   ", "Limpieza Diaria", energy, reqs));
    }
    
    @Test
    void testGivenExternalMapModifiedWhenGettingRequirementsThenProgramMapRemainsUnchanged() {
        Map<CleaningProduct, Integer> originalReqs = new HashMap<>();
        originalReqs.put(CleaningProduct.WATER, 2);
        Energy energy = Energy.of(10);
        
        CleaningProgram program = new CleaningProgram("P01", "Diaria", energy, originalReqs);
        
        // Modificamos el mapa original después de crear el programa
        originalReqs.put(CleaningProduct.SOAP, 1);
        
        // La especificación exige copia defensiva: el programa no debe tener SOAP
        assertNull(program.getRequirements().get(CleaningProduct.SOAP));
    }

    @Test
    void testGivenProgramsWithSameIdButDifferentAttributesWhenComparingThenTheyAreEqual() {
        Map<CleaningProduct, Integer> reqs = new HashMap<>();
        Energy energy1 = Energy.of(10);
        Energy energy2 = Energy.of(20);
        
        CleaningProgram program1 = new CleaningProgram("P01", "Diaria", energy1, reqs);
        CleaningProgram program2 = new CleaningProgram("P01", "Semanal", energy2, reqs);
        
        // La especificación dice: Igualdad basada ÚNICAMENTE en 'id'
        assertEquals(program1, program2);
    }
}