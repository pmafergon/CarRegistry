package com.mafer.CarRegisty.service.domain;

import com.mafer.CarRegisty.repository.entity.BrandEntity;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    private Integer id;
    private String brandname;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fueltype;
    private Integer numdoors;

    private Brand brand;


}
