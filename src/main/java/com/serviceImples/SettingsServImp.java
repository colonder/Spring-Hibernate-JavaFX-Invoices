package com.serviceImples;

import com.entity.Settings;
import com.repositories.ISettingsRepository;
import com.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServImp implements ISettingsService {

    @Autowired
    ISettingsRepository settingsRepository;

    @Override
    public Settings save(Settings entity) {
        return settingsRepository.save(entity);
    }

    @Override
    public Settings findById(int id) {
        return settingsRepository.findById(id).orElseGet(Settings::new);
    }
}
