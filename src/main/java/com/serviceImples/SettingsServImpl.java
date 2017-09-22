package com.serviceImples;

import com.entity.Settings;
import com.repositories.ISettingsRepository;
import com.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SettingsServImpl implements ISettingsService {
    @Autowired
    ISettingsRepository settingsRepository;

    @Override
    public List<Settings> findAll() {
        return settingsRepository.findAll();
    }

    @Override
    public Settings save(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Override
    public void delete(Settings settings) {
        settingsRepository.delete(settings);
    }
}
