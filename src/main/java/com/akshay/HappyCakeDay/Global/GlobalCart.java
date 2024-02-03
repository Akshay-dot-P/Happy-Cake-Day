package com.akshay.HappyCakeDay.Global;

import com.akshay.HappyCakeDay.models.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalCart {
    public static List<Product> cart;

    static{
        cart = new ArrayList<Product>();
    }
}
