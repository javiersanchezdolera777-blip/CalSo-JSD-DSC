package com.calso.robot.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.calso.robot.domain.model.CleaningProduct;
import com.calso.robot.domain.model.CleaningProgram;
import com.calso.robot.domain.model.ExecutionResult;
import com.calso.robot.domain.valueobjects.Energy;

class ExecuteCleaningProgramUseCaseTest {
    @Test
    void testGivenWater2WhenExecutingProgramRequiringWater2ThenExecutesAndConsumesWater2() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));
        CleaningProgram program = new CleaningProgram("CP-08", "Basic cleaning",Energy.of(10), Map.of(CleaningProduct.WATER, 2));
        ExecuteCleaningProgramUseCase useCase = new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);

        assertTrue(result.executed());
        assertTrue(result.missingProducts().isEmpty());
        assertEquals(0, inventory.available(CleaningProduct.WATER));
    }
    @Test
    void testGivenWater1WhenExecutingProgramRequiringWater3ThenReportsWater2MissingAndPreservesStock() {

        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 1));
        CleaningProgram program = new CleaningProgram( "CP-09","Basic cleaning",Energy.of(10), Map.of(CleaningProduct.WATER, 3));
        ExecuteCleaningProgramUseCase useCase = new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        assertAll(
                () -> assertFalse(result.executed()),
                () -> assertEquals(
                        Map.of(CleaningProduct.WATER, 2),
                        result.missingProducts()),
                () -> assertEquals(
                        1,
                        inventory.available(CleaningProduct.WATER))
        );
    }
    @Test
    void testGivenWater3WhenExecutingProgramRequiringWater2ThenExecutesAndLeavesWater1() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 3));
        CleaningProgram program = new CleaningProgram("CP-10", "Basic cleaning", Energy.of(10), Map.of(CleaningProduct.WATER, 2));
        ExecuteCleaningProgramUseCase useCase = new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        
        assertTrue(result.executed());
        assertTrue(result.missingProducts().isEmpty());
        assertEquals(1, inventory.available(CleaningProduct.WATER));
    }
    @Test
    void testGivenWater1AndSop0AndCleaner2WhenExecutingProgramRequiringWater3AndSoap2AndCleaner3ThenReportsBothMissingAndPreservesStock() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 1,CleaningProduct.SOAP, 0, CleaningProduct.GLASS_CLEANER, 2)
        );

        CleaningProgram program = new CleaningProgram(
                "CP-11",
                "Cleaning requiring water and soap",
                Energy.of(10),
                Map.of(CleaningProduct.WATER, 3, CleaningProduct.SOAP, 2, CleaningProduct.GLASS_CLEANER,3)
        );
        ExecuteCleaningProgramUseCase useCase =new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        assertAll(
                () -> assertFalse(result.executed()),
                () -> assertEquals(
                        Map.of(
                                CleaningProduct.WATER, 2,
                                CleaningProduct.SOAP, 2,
                                CleaningProduct.GLASS_CLEANER, 1
                        ),
                        result.missingProducts()
                ),
                () -> assertEquals(1, inventory.available(CleaningProduct.WATER)),
                () -> assertEquals(0, inventory.available(CleaningProduct.SOAP)),
                () -> assertEquals(2, inventory.available(CleaningProduct.GLASS_CLEANER))
        );
    }
    @Test
    void testGivenSufficientWaterAndInsufficientWAXWhenExecutingProgramThenRejectsWithoutConsumingWater() {
        StubCleaningInventory inventory = new StubCleaningInventory(
            Map.of(CleaningProduct.WATER, 3,CleaningProduct.WAX, 0
            )
        );
        Map<CleaningProduct, Integer> requirements =
            new EnumMap<>(CleaningProduct.class);

        requirements.put(CleaningProduct.WATER, 2);
        requirements.put(CleaningProduct.WAX, 1);
        CleaningProgram program = new CleaningProgram("CP-12", "Cleaning requiring water and wax", Energy.of(10),requirements);
        ExecuteCleaningProgramUseCase useCase= new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        
        assertAll(
            () -> assertFalse(result.executed()),
            () -> assertEquals(
                Map.of(CleaningProduct.WAX, 1),
                result.missingProducts()
            ),
            () -> assertEquals(3, inventory.available(CleaningProduct.WATER)),
            () -> assertEquals(0, inventory.available(CleaningProduct.WAX))
        );
    }
    @Test
    void testGivenProgramWithoutProductsWhenExecutingProgramThenExecutesWithoutConsumingInventory() {
        StubCleaningInventory inventory = new StubCleaningInventory(
            Map.of(CleaningProduct.WATER,0,CleaningProduct.SOAP, 0, CleaningProduct.GLASS_CLEANER,0, CleaningProduct.WAX,0));
        CleaningProgram program = new CleaningProgram("CP-13","Cleaning without products",Energy.of(10),Map.of());
        ExecuteCleaningProgramUseCase useCase= new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        assertAll(
            () -> assertTrue(result.executed()),
            () -> assertTrue(result.missingProducts().isEmpty()),
            () -> assertEquals(0, inventory.available(CleaningProduct.WATER)),
            () -> assertEquals(0, inventory.available(CleaningProduct.SOAP)),
            () -> assertEquals(0, inventory.available(CleaningProduct.GLASS_CLEANER)),
            () -> assertEquals(0, inventory.available(CleaningProduct.WAX))
        );
    }
    
    @Test
    void testGivenZeroProductWhenExecutingProgramThenExecutesWithoutConsumingInventory() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 10));
        CleaningProgram program = new CleaningProgram("CP-14","Cleaning with zero product requirement", Energy.of(10), Map.of(CleaningProduct.WATER, 0));
        ExecuteCleaningProgramUseCase useCase = new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);
        assertAll(
            () -> assertTrue(result.executed()),
            () -> assertTrue(result.missingProducts().isEmpty()),
            () -> assertEquals(10, inventory.available(CleaningProduct.WATER))
        );
    }
}
