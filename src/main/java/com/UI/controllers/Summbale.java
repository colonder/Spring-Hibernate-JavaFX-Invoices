package com.UI.controllers;

import com.entity.BoughtProducts;

import java.math.BigDecimal;

@FunctionalInterface
public interface Summbale {
    BigDecimal getValueFrom(BoughtProducts product);
}
