package com.serviceImples;

import com.entity.Settings;
import com.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServImp implements ISettingsService {

    @Autowired
    ISettingsService settingsService;

    @Override
    public Settings save(Settings entity) {
        return settingsService.save(entity);
    }

    @Override
    public Settings update(Settings entity) {
        return settingsService.update(entity);
    }
}
