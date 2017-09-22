package com.UI.controllers;

import com.entity.BoughtProduct;

import java.math.BigDecimal;

@FunctionalInterface
public interface Summbale {
    BigDecimal getValueFrom(BoughtProduct product);
}
