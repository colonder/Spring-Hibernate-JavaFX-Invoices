package com.UI.controllers;

import com.entity.Product;
import org.springframework.stereotype.Controller;

@Controller
public class NewProductPresenter implements IInitializableFromEntity<Product> {
    @Override
    public void initializeFields(Product entity) {

    }
}
