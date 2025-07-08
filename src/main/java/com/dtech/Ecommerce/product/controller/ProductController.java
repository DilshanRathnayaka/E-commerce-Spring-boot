package com.dtech.Ecommerce.product.controller;

import com.dtech.Ecommerce.product.dto.*;
import com.dtech.Ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:33 PM
 */

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.OK);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/updateStock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Integer id, @RequestBody StockDTO stockDTO) {
        return new ResponseEntity<>(productService.updateStock(id, stockDTO), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/productCount")
    public ResponseEntity<?> getProductCount() {
        return new ResponseEntity<>(productService.getProductCount(), HttpStatus.OK);
    }

    @PostMapping("/categoryCreate")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(productService.saveCategory(categoryDTO), HttpStatus.OK);
    }

    @PostMapping("/categorySubCreate")
    public ResponseEntity<?> saveSubCategory(@RequestBody SubCategoryDTO subCategoryDTO) {
        return new ResponseEntity<>(productService.saveSubCategory(subCategoryDTO), HttpStatus.OK);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(productService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/getByUuid/{uuid}")
    public ResponseEntity<?> getProductByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(productService.getProductByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping("/getcategorySubByCategory/{categoryID}")
    public ResponseEntity<?> getcategorySubByCategoryID(@PathVariable Integer categoryID) {
        return new ResponseEntity<>(productService.getcategorySubByCategoryID(categoryID), HttpStatus.OK);
    }

    @PostMapping("/getcategoryByID/{categoryID}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Integer categoryID) {
        return new ResponseEntity<>(productService.getCategoryByID(categoryID), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public List<String> generateSKUs(@RequestBody SkuRequestDTO request) {
        return productService.generateSKUs(request);
    }

    @PostMapping("/searchByQuery")
    public ResponseEntity<?> searchProductByQuery(@RequestParam String searchQuery) {
        return new ResponseEntity<>(productService.searchProductByQuery(searchQuery), HttpStatus.OK);
    }

    @PostMapping("/getProductByCategoryID/{categoryID}")
    public ResponseEntity<?> getProductListByCategoryID(@PathVariable Integer categoryID, @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(productService.getProductListByCategoryID(categoryID, pageable), HttpStatus.OK);
    }

    @PostMapping("/getProductByCategoryIDANDSubcategory/{categoryID}/{subCategoryID}")
    public ResponseEntity<?> getProductByCategoryIDSubcategory(@PathVariable Integer categoryID, @PathVariable Integer subCategoryID,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(productService.getProductByCategoryIDSubcategory(categoryID, subCategoryID,pageable), HttpStatus.OK);
    }

    @PostMapping("/reviewProduct")
    public ResponseEntity<?> reviewProductBy(@RequestBody ReviewDTO reviewDTO) {
        productService.reviewProductBy(reviewDTO);
        return ResponseEntity.ok("Review Added");
    }

    @PostMapping("/getReviewProduct/{productId}")
    public ResponseEntity<?> getReviewProduct(@PathVariable Integer productId) {
       return new ResponseEntity<>(productService.getReviewProduct(productId),HttpStatus.OK)  ;
    }

    @DeleteMapping("/deleteCategoryById/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
        productService.deleteCategoryById(id);
        return ResponseEntity.ok("category Deleted");
    }

    @DeleteMapping("/subCategoryDelete/{id}")
    public ResponseEntity<?> subCategoryDelete(@PathVariable Integer id) {
        productService.subCategoryDelete(id);
        return ResponseEntity.ok("Sub category Deleted");
    }

}
