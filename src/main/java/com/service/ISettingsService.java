package com.service;

import com.entity.Settings;

public interface ISettingsService {
    Settings save(Settings entity);

    Settings update(Settings entity);
}
