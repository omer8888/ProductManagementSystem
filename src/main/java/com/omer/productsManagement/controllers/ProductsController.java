package com.omer.productsManagement.controllers;

import com.omer.productsManagement.models.Product;
import com.omer.productsManagement.models.ProductDto;
import com.omer.productsManagement.service.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("products")
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @GetMapping({"", "/"})
    public String showProducts(Model model) {
        List<Product> products = productsService.getAllProducts();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "products/createProduct";
    }

    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        productsService.verifyImage(productDto, result);
        if (result.hasErrors()) {
            return "products/createProduct";
        }
        productsService.uploadNewProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        Optional<Product> optionalProduct = productsService.getProductById(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("No product found with id " + id);
            return "redirect:/products";
        }
        Product product = optionalProduct.get();

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setBrand(product.getBrand());

        model.addAttribute("productDto", productDto);
        model.addAttribute("product", product);

        return "products/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "products/editProduct";
        }
        productsService.uploadExistingProduct(id, productDto);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(Model model, @RequestParam int id) {
        productsService.deleteProduct(id);
        return "redirect:/products";
    }
}
