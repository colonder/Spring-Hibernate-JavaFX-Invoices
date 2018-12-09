package com.repositories;

import com.entity.Numbering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INumberingRepository extends JpaRepository<Numbering, Integer> {
}
