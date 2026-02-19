package com.codingairbnb.project.airBnbApp.strategy;

import com.codingairbnb.project.airBnbApp.entities.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapper;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapper.calculatePrice(inventory);
        return price.multiply(inventory.getSurgeFactor());
    }
}
