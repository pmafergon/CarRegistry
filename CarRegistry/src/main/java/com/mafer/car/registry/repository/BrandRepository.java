package com.mafer.car.registry.repository;

import com.mafer.car.registry.repository.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity,Integer> {


    @Query("SELECT b FROM BrandEntity b WHERE b.name = :name")
    Optional<BrandEntity> findByName(@Param("name") String name);
}
