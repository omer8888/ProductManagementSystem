package com.omer.productsManagement.service;

import com.omer.productsManagement.dao.ProductsRepository;
import com.omer.productsManagement.models.Product;
import com.omer.productsManagement.models.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public void verifyImage(ProductDto productDto, BindingResult result, boolean shouldSkip) {
        if (shouldSkip) {
            return;
        }
        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "Image file is empty"));
        }
    }

    public String uploadImage(ProductDto productDto) {
        MultipartFile image = productDto.getImageFile();
        String uploadDir = "public/images/";
        Path uploadPath = Paths.get(uploadDir);
        String storageFileName = new Date().getTime() + "_" + image.getOriginalFilename();

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Failed to upload file: " + e.getMessage());
            return null;
        }
        return storageFileName;
    }

    public void saveProduct(String storageFileName, ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setBrand(productDto.getBrand());
        product.setCreateAt(new Date());
        product.setImageFileName(storageFileName);
        save(product);
    }

    private void save(Product product) {
        productsRepository.save(product);
    }

    public void uploadNewProduct(ProductDto productDto) {
        String storageFileName = uploadImage(productDto);
        if (storageFileName != null) {
            saveProduct(storageFileName, productDto);
        }
    }

    public void uploadExistingProduct(int id, ProductDto productDto) {
        Optional<Product> optionalProduct = getProductById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setId(id);
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setBrand(productDto.getBrand());
            product.setCreateAt(new Date());

            MultipartFile imageFile = productDto.getImageFile();
            if (imageFile != null) {
                String imagePath = uploadImage(productDto);
                product.setImageFileName(imagePath);
            }

            save(product);
        }
    }

    public List<Product> getAllProducts(boolean isSorted) {
        if (isSorted) {
            return productsRepository.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
        }
        return productsRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return getAllProducts(false);
    }

    public Optional<Product> getProductById(int id) {
        return productsRepository.findById(id);
    }

    public void deleteProduct(int id) {
        productsRepository.deleteById(id);
    }
}
