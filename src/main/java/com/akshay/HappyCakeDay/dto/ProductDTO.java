package com.akshay.HappyCakeDay.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductDTO {
@Getter @Setter
    private Long id;
    private String name;
    private int categoryID ;
    private double price;
    private double weight;
    private String description;
    private String imageName;
}
