package com.calso.robot.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;
import com.calso.robot.domain.model.CleaningProduct;

class StubCleaningInventoryTest {

    @Test
    void testGivenUnknownProductWhenCheckingAvailableThenReturnsZero() {
        StubCleaningInventory inventory = new StubCleaningInventory();
        assertEquals(0, inventory.available(CleaningProduct.WATER));
    }

    @Test
    void testGivenStock2WaterWhenConsuming1WaterThenStockBecomes1() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));
        inventory.consume(CleaningProduct.WATER, 1);
        assertEquals(1, inventory.available(CleaningProduct.WATER));
    }

    @Test
    void testGivenStock1WaterWhenConsuming2WaterThenThrowsIllegalArgumentExceptionAndPreservesStock() {
        StubCleaningInventory inventory = new StubCleaningInventory(Map.of(CleaningProduct.WATER, 1));
        assertThrows(IllegalArgumentException.class, () -> inventory.consume(CleaningProduct.WATER, 2));
        assertEquals(1, inventory.available(CleaningProduct.WATER));
    }
    @Test
    void testGivenStockWater1WhenConsumingWaterMinus1ThenThrowsIllegalArgumentExceptionAndPreservesStock() {
        StubCleaningInventory inventory =
                new StubCleaningInventory(Map.of(CleaningProduct.WATER, 1));

        assertThrows(IllegalArgumentException.class,
                () -> inventory.consume(CleaningProduct.WATER, -1));

        assertEquals(1, inventory.available(CleaningProduct.WATER));
    }

    @Test
    void testGivenStockWater2WhenConsumingWater2ThenStockBecomes0() {
        StubCleaningInventory inventory =
                new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));

        inventory.consume(CleaningProduct.WATER, 2);

        assertEquals(0, inventory.available(CleaningProduct.WATER));
    }

    @Test
    void testGivenStockWater2WhenConsumingWater0ThenStockRemains2() {
        StubCleaningInventory inventory =
                new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));

        inventory.consume(CleaningProduct.WATER, 0);

        assertEquals(2, inventory.available(CleaningProduct.WATER));
    }

    @Test
    void testGivenTwoInventoriesWithWater2WhenConsumingWater1FromFirstThenSecondKeepsWater2() {
        StubCleaningInventory first =
                new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));

        StubCleaningInventory second =
                new StubCleaningInventory(Map.of(CleaningProduct.WATER, 2));

        first.consume(CleaningProduct.WATER, 1);

        assertEquals(1, first.available(CleaningProduct.WATER));
        assertEquals(2, second.available(CleaningProduct.WATER));
    }
}