package com.repositories;

import com.entity.Numbering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INumberingRepository extends JpaRepository<Numbering, Integer> {
}
