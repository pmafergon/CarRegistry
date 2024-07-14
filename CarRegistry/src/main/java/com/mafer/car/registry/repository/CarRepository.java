package com.mafer.car.registry.repository;

import com.mafer.car.registry.repository.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<CarEntity,Integer> {

    @Query("SELECT c FROM CarEntity c ORDER BY c.id DESC")
    List<CarEntity> findLastCar();


}
