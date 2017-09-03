package com.UI.controllers;

import com.entity.BaseAbstractEntity;

public interface IInitializableFromEntity {
    void initializeFields(BaseAbstractEntity entity);
}
