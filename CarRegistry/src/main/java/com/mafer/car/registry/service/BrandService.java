package com.mafer.car.registry.service;

import com.mafer.car.registry.service.domain.Brand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    List<Brand> findAllB();
    CompletableFuture<List<Brand>> saveB(List<Brand> brand) throws Exception;
    String deleteB(Integer id);
    Brand updateB(Integer id, Brand brand) throws Exception;
    Brand getBrandById(Integer id);

}
