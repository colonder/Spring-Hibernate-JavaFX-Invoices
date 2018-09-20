package com.serviceImples;

import com.entity.Numbering;
import com.repositories.INumberingRepository;
import com.service.INumberingService;
import org.springframework.beans.factory.annotation.Autowired;

public class NumberingServImp implements INumberingService {

    @Autowired
    INumberingRepository invoiceRepository;

    @Override
    public Numbering save(Numbering entity) {
        return invoiceRepository.save(entity);
    }

    @Override
    public Numbering findById(int id) {
        return invoiceRepository.findById(id).orElseGet(Numbering::new);
    }
}
