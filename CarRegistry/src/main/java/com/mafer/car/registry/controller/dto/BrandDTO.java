package com.mafer.car.registry.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTO {
        private Integer id;
        private String name;
        private Integer warranty;
        private String country;
}
