package com.mafer.CarRegisty.controller.dto;


import com.mafer.CarRegisty.repository.entity.BrandEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTOResponse {
    private Integer id;
    //private Integer brand_id;
    private String model;
    private Integer milleage;
    private Double price;
    private Integer year;
    private String description;
    private String colour;
    private String fueltype;
    private Integer numdoors;
    private BrandDTO brand;
}
