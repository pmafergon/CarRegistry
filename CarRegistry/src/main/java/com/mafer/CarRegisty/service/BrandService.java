package com.mafer.CarRegisty.service;

import com.mafer.CarRegisty.service.domain.Brand;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    CompletableFuture<List<Brand>> findAllB() ;
    CompletableFuture<List<Brand>> saveB(List<Brand> brand) throws Exception;
    String deleteB(Integer id);
    Brand updateB(Integer id, Brand brand) throws Exception;
    Brand getBrandById(Integer id);

}
