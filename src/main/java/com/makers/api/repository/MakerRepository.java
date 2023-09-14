package com.makers.api.repository;

import com.makers.api.entity.MakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MakerRepository extends JpaRepository<MakerEntity, Long> {
}
