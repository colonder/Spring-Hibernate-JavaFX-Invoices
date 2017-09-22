package com.service;

import com.entity.Settings;

import java.util.List;

public interface ISettingsService {
    List<Settings> findAll();
    Settings save(Settings settings);
    void delete(Settings settings);
}
