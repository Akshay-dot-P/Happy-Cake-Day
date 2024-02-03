package com.akshay.HappyCakeDay.Controller;

import com.akshay.HappyCakeDay.dto.ProductDTO;
import com.akshay.HappyCakeDay.models.Category;
import com.akshay.HappyCakeDay.models.Product;
import com.akshay.HappyCakeDay.service.CategoryService;
import com.akshay.HappyCakeDay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;

@Controller
public class CategoriesController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    public static String uploadDir= System.getProperty("user.dir") + "/src/main/resources/static/ProductImages";
    @GetMapping("/adminus")
    public String adminus(){
        return "admin";
    }



    @GetMapping("/adminus/categories")
    public String categories(Model model) {
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/adminus/categories/add")
    public String addcategories(Model theModel){
        theModel.addAttribute("category", new Category());
        return "categoriesAdd";
    }
    @PostMapping("/adminus/categories/add")
    public String Paddcategories(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/adminus/categories";
    }
    @GetMapping("/adminus/categories/delete/{id}")
    public String DeleteById(@PathVariable int id){
       categoryService.removeCategoryById(id);
       return "redirect:/adminus/categories";
   }

    @GetMapping("/adminus/categories/update/{id}")
    public String DeleteById(@PathVariable int id, Model themodel){
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()){
            themodel.addAttribute("category",category.get());
            return "categoriesAdd";
        }else{
                return "404";
            }

    }

    //products
    @GetMapping("/adminus/products")
    public String getProducts(Model themodel){
        themodel.addAttribute("products", productService.getAllProducts());
        themodel.addAttribute("productsDTO", productService.getAllProducts());


        return "products";
    }

    @GetMapping("/adminus/products/add")
    public String addProducts(Model themodel){
        themodel.addAttribute("productDTO", new ProductDTO());
        themodel.addAttribute("categories", categoryService.getAllCategory());

        return "productsAdd";
    }


    @PostMapping("/adminus/products/add")
    public String addProductspost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                    @RequestParam("ProductImage")MultipartFile file,
                                    @RequestParam("imgName")String imgName) throws IOException {
        Product product= new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        int categoryId = productDTO.getCategoryID();
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }
        else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);

        product.setCategory(categoryService.getCategoryById(categoryId).orElseThrow(()
                -> new NoSuchElementException("Category not found for ID: " + categoryId)));
        productService.addproduct(product);
        return "redirect:/adminus/products";
    }


@GetMapping("/adminus/products/delete/{id}")
public String pDeleteById(@PathVariable long id){
    productService.removeProduct(id);
    return "redirect:/adminus/products";
}

@GetMapping("/adminus/products/update/{id}")
public String hDeleteById(@PathVariable long id, Model themodel){
    Product product = productService.getproductbyid(id).get();
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setCategoryID(product.getCategory().getId());
    productDTO.setPrice(product.getPrice());
    productDTO.setWeight(product.getWeight());
    productDTO.setDescription(product.getDescription());
    productDTO.setImageName(product.getImageName());
    themodel.addAttribute("categories", categoryService.getAllCategory());
    themodel.addAttribute("productDTO",productDTO);
    return "productsAdd";

}

}
