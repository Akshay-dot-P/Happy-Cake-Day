package com.akshay.HappyCakeDay.Controller;

import com.akshay.HappyCakeDay.Global.GlobalCart;
import com.akshay.HappyCakeDay.service.CategoryService;
import com.akshay.HappyCakeDay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;
    @GetMapping({"/","/homepage"})
    public String home(Model themodel){
        themodel.addAttribute("countofcart",GlobalCart.cart.size());

        return "home";
    }

    @GetMapping("/shop")
    public String shop(Model themodel){
        themodel.addAttribute("products",productService.getAllProducts());
        themodel.addAttribute("categories",categoryService.getAllCategory());
        themodel.addAttribute("countofcart",GlobalCart.cart.size());

        return "shop";
    }
    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model themodel, @PathVariable int id){
        themodel.addAttribute("products",productService.getAllProductsByCategoryId(id));
        themodel.addAttribute("categories",categoryService.getAllCategory());
        themodel.addAttribute("countofcart",GlobalCart.cart.size());

        return "shop";
    }
    @GetMapping("/shop/viewproduct/{id}")
    public String viewproduct(Model themodel, @PathVariable int id){
        themodel.addAttribute("product",productService.getproductbyid(id));
        System.out.println("Product Details: " + productService.getproductbyid(id));
        themodel.addAttribute("countofcart", GlobalCart.cart.size());

        return "viewproduct";
    }

    
}
