package com.mafer.CarRegisty.service;

import com.mafer.CarRegisty.service.domain.Brand;
import com.mafer.CarRegisty.service.domain.Car;

import java.util.List;

public interface BrandService {

    List<Brand> findAllB() throws Exception;
    Brand saveB(Brand brand) throws Exception;
    String deleteB(Integer id);
    Brand updateB(Integer id, Brand brand) throws Exception;
    Brand getBrandById(Integer id);

}
