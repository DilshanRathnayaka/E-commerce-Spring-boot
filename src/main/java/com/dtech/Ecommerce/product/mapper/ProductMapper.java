package com.dtech.Ecommerce.product.mapper;

import com.dtech.Ecommerce.product.dto.CategoryDTO;
import com.dtech.Ecommerce.product.dto.ProductDTO;
import com.dtech.Ecommerce.product.dto.ReviewDTO;
import com.dtech.Ecommerce.product.dto.SubCategoryDTO;
import com.dtech.Ecommerce.product.model.Category;
import com.dtech.Ecommerce.product.model.Product;
import com.dtech.Ecommerce.product.model.Review;
import com.dtech.Ecommerce.product.model.SubCategory;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:42 PM
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Named("toDTO")
    ProductDTO toDTO(Product product);

    @Mapping(target = "stock.product", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ProductDTO> toDTOList(List<Product> products);

    Category categorytoEntity(CategoryDTO categoryDTO);

    @Named("categorytoDTOList")
    @Mapping(target = "subCategories", source = "subCategories")
    CategoryDTO categorytoDTO(Category category);

    @IterableMapping(qualifiedByName = "categorytoDTOList")
    List<CategoryDTO> categorytoDTOList(List<Category> categories);

    @Mapping(target = "category.id", source = "category")
    SubCategory subCategoryDTOtoSubcategory (SubCategoryDTO subCategoryDTO);

    @Named("subCategorytoDTOList")
    @Mapping(target = "category", source = "category.id")
    SubCategoryDTO subCategorytoDTO(SubCategory subCategory);

    @IterableMapping(qualifiedByName = "subCategorytoDTOList")
    List<SubCategoryDTO> subCategorytoDTOList(List<SubCategory> subCategories);

    Review DTOtoReviewEntity(ReviewDTO reviewDTO);

    ReviewDTO ReviewEntityToDTO(Review review);

}
