package com.omer.productsManagement.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

//model data transfer obj
public class ProductDto {

    @NotEmpty(message= "name required")
    private String name;

    @NotEmpty(message= "brand required")
    private String brand;

    @NotEmpty(message= "category required")
    private String category;

    @Min(0)
    private Double price;

    @Size(min=10, message = "description should be at least 10 characters")
    @Size(max=400, message = "description cannot exceed 400 characters")
    private String description;

    private MultipartFile imageFile;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

}
