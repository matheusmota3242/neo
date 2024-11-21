package com.m2g2.neo.repository;

import com.m2g2.neo.model.PositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionRecord, Long> {
}
