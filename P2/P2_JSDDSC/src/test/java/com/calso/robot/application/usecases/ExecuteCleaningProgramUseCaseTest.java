package com.calso.robot.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        CleaningProgram program = new CleaningProgram(
                "P1",
                "Basic cleaning",
                Energy.of(10),
                Map.of(CleaningProduct.WATER, 2));

        ExecuteCleaningProgramUseCase useCase = new ExecuteCleaningProgramUseCase(inventory);
        ExecutionResult result = useCase.execute(program);

        assertTrue(result.executed());
        assertTrue(result.missingProducts().isEmpty());
        assertEquals(0, inventory.available(CleaningProduct.WATER));
    }
}
