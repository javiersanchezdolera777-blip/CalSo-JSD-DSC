package com.calso.robot.domain.valueobjects;

import java.util.Objects;

public final class Energy {
    private final int units;
    
    private Energy(int units) { 
        this.units = units; 
    }
    
    public static Energy of(int units) {
        if (units <= 0) 
            throw new IllegalArgumentException("Energy must be positive");
        return new Energy(units);
    }
    
    public int getValue() { 
        return units; 
    }
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Energy other = (Energy) obj;
		return units == other.units;
	}
    
    @Override
	public int hashCode() {
		return Objects.hash(units);
	}
}
