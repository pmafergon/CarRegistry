package com.mafer.CarRegisty.repository;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BRepository extends JpaRepository<BrandEntity,Integer> {
    @Query("SELECT c FROM BrandEntity c ORDER BY c.id DESC")
    List<BrandEntity> findLastBrand();
}
