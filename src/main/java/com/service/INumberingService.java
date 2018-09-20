package com.service;

import com.entity.Numbering;

public interface INumberingService {
    Numbering save(Numbering entity);

    Numbering findById(int id);
}
