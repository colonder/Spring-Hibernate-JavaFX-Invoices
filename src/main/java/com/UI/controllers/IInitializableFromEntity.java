package com.UI.controllers;

import com.entity.BaseAbstractEntity;

public interface IInitializableFromEntity<E extends BaseAbstractEntity> {
    void initializeFields(E entity);
}
