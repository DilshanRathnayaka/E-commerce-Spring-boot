package com.dtech.Ecommerce.product.service;

import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.product.dto.*;
import com.dtech.Ecommerce.product.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:27 PM
 */
public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO getProductById(Integer id);

    StockDTO updateStock(Integer id,StockDTO stockDTO);

    Page<ProductDTO> getAllProducts(Pageable pageable);

    void deleteProduct(Integer id);

    CommonResponse getProductCount();

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    SubCategoryDTO saveSubCategory(SubCategoryDTO subCategoryDTO);

    List<CategoryDTO> getAllCategories();

    ProductDTO getProductByUuid(String uuid);

    List<SubCategoryDTO> getcategorySubByCategoryID(Integer categoryID);

    CategoryDTO getCategoryByID(Integer id);

    List<String> generateSKUs(SkuRequestDTO skuRequestDTO);

    List<ProductDTO> searchProductByQuery(String query);

    Page<ProductDTO> getProductListByCategoryID(Integer categoryID,Pageable pageable);

    Page<ProductDTO> getProductByCategoryIDSubcategory(Integer categoryID, Integer subCategoryID,Pageable pageable);

    void reviewProductBy(ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewProduct(Integer id);

    void deleteCategoryById(Integer id);

    void subCategoryDelete(Integer id);



}
