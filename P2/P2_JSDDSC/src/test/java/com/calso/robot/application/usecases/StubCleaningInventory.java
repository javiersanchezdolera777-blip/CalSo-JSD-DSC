package com.calso.robot.application.usecases;

import java.util.EnumMap;
import java.util.Map;
import com.calso.robot.domain.model.CleaningProduct;
import com.calso.robot.domain.repositories.ICleaningInventory; // Revisa la importación según tu proyecto

public final class StubCleaningInventory implements ICleaningInventory {

    private final Map<CleaningProduct, Integer> stock = new EnumMap<>(CleaningProduct.class);

    public StubCleaningInventory() {
    }

    public StubCleaningInventory(Map<CleaningProduct, Integer> initialStock) {
        if (initialStock != null) {
            initialStock.forEach((product, qty) -> {
                if (product != null && qty != null && qty >= 0) {
                    this.stock.put(product, qty);
                }
            });
        }
    }

    @Override
    public int available(CleaningProduct product) {
        if (product == null) {
            return 0;
        }
        return stock.getOrDefault(product, 0);
    }

    @Override
    public void consume(CleaningProduct product, int quantity) {
        if (product == null || quantity < 0) {
            throw new IllegalArgumentException("Cantidad o producto no válido");
        }
        int current = available(product);
        if (quantity > current) {
            throw new IllegalArgumentException("Stock insuficiente de " + product);
        }
        stock.put(product, current - quantity);
    }
}