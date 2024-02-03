package com.akshay.HappyCakeDay.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name="products")
public class Product {
    @Getter
    @Setter

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName= "category_id")

    private Category category;

    private double price;
    private double weight;
    private String description;
    private String imageName;






}
