package com.calso.robot.application.usecases;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import com.calso.robot.application.ports.inbound.IExecuteCleaningProgram;
import com.calso.robot.domain.model.CleaningProduct;
import com.calso.robot.domain.model.CleaningProgram;
import com.calso.robot.domain.model.ExecutionResult;
import com.calso.robot.domain.repositories.ICleaningInventory;

public final class ExecuteCleaningProgramUseCase implements IExecuteCleaningProgram {
    
    private final ICleaningInventory inventory;
    
    public ExecuteCleaningProgramUseCase(ICleaningInventory inventory) {
        this.inventory = Objects.requireNonNull(inventory, "inventory");
    }
    
    @Override
    public ExecutionResult execute(CleaningProgram program) {
        Objects.requireNonNull(program, "program");
        Map<CleaningProduct,Integer> missing = new EnumMap<>(CleaningProduct.class);
        
     //Solución error: Hacer dos bucles 
        for (var requirement : program.getRequirements().entrySet()) {
            CleaningProduct product = requirement.getKey();
            int required = requirement.getValue();
            int available = inventory.available(product);
            if (available < required) {
                missing.put(product, required - available);
            }
        }
        
        if (!missing.isEmpty()) {
            return new ExecutionResult(false, missing);
        }
        for (var requirement : program.getRequirements().entrySet()) {
            inventory.consume(requirement.getKey(), requirement.getValue());
        }

        return new ExecutionResult(true, Map.of());
    }
}
