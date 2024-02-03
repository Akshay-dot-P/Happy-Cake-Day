package com.akshay.HappyCakeDay.Controller;

import com.akshay.HappyCakeDay.Global.GlobalCart;
import com.akshay.HappyCakeDay.models.Product;
import com.akshay.HappyCakeDay.service.CategoryService;
import com.akshay.HappyCakeDay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/addtocart/{id}")
    public String addtocart(@PathVariable int id){

        GlobalCart.cart.add(productService.getproductbyid(id).get());

        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public String veiwcart(Model themodel){
        themodel.addAttribute("countofcart",GlobalCart.cart.size());
        themodel.addAttribute("totalofcart",GlobalCart.cart.stream().mapToDouble(Product::getPrice).sum());
        themodel.addAttribute("cart",GlobalCart.cart);

        return "cart";
    }
}
