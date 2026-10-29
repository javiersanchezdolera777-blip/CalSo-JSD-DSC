package com.calso.robot.domain.model;

public enum CleaningProduct {
    WATER("agua"), SOAP("jabón"), WAX("cera"), GLASS_CLEANER("limpiacristales");  // cambiamos "cristales" por "limpiacristales" en el enum
    
    private final String label;
    
    CleaningProduct(String label) { 
        this.label = label; 
    }
    
    @Override
    public String toString() { 
        return label; 
    }
}
