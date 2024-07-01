package com.mafer.CarRegisty.repository;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BRepository extends JpaRepository<BrandEntity,Integer> {


    @Query("SELECT b FROM BrandEntity b WHERE b.name = :name")
    Optional<BrandEntity> findByName(@Param("name") String name);
}
