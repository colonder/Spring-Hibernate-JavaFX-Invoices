package com.repositories;

import com.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISettingsRepository extends JpaRepository<Settings, Integer> {
}
