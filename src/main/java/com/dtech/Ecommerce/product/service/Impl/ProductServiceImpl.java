package com.dtech.Ecommerce.product.service.Impl;

import com.dtech.Ecommerce.exeption.ProductExeption;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import com.dtech.Ecommerce.order.orderService.impl.OrderServiceImpl;
import com.dtech.Ecommerce.product.dto.*;
import com.dtech.Ecommerce.product.mapper.ProductMapper;
import com.dtech.Ecommerce.product.model.*;
import com.dtech.Ecommerce.product.repository.*;
import com.dtech.Ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Nimesh Dilshan
 * User:nimesh_r
 * Date:1/6/2025
 * Time:2:27 PM
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;
    private final ProductMapper productMapper;
    private final StockRepo stockRepo;
    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;
    private final DocumentDataRepo documentDataRepo;
    private final ReviewRepo reviewRepo;
    private final OrderServiceImpl orderServiceImpl;
    private final VarientAttRepo  varientAttRepo;

    public ProductDTO saveProduct(ProductDTO productDTO) {
        try {
            if(productDTO.getId() == null){
                    productDTO.setUuid(UUID.randomUUID().toString());
            }
            Product product = productMapper.toEntity(productDTO);

            if (productDTO.getImage() != null) {
                for (DocumentData documentData : product.getImage()) {
                    documentData.setProduct(product);
                }
            } else {
                throw new ProductExeption("Image is required");
            }
            if(product.getStock() != null){
                product.getStock().setProduct(product);
            }
            if (product.getVariant() != null) {
                try {
                    for (VariantAttribute variant : product.getVariant()) {
                        variant.setProduct(product);
                        Stock stock = variant.getStock();
                        stock.setVariant(variant);
                            if(variant.getImage().getId() != null){
                                Optional<DocumentData> documentData2 = documentDataRepo.findById(variant.getImage().getId());
                                if (documentData2.isPresent()) {
                                    DocumentData existingDocumentData = documentData2.get();
                                    DocumentData documentData = variant.getImage();
                                    documentData.setVariant(variant);
                                    if (variant.getImage().getDocUUID() != null) {
                                        existingDocumentData.setDocUUID(variant.getImage().getDocUUID());
                                    }
                                    documentDataRepo.save(existingDocumentData);
                                }
                            }else{
                                DocumentData documentData = variant.getImage();
                                documentData.setVariant(variant);
                            }
                    }
                }catch (ProductExeption e){
                    throw new ProductExeption("Error occurred while saving the variant");
                }
            }
            Product savedProduct = productRepository.save(product);
            return productMapper.toDTO(savedProduct);
        } catch (ProductExeption e) {
            throw new ProductExeption(e.getMessage());
        }
    }


    public ProductDTO getProductById(Integer id) {
        try {
            return productMapper.toDTO(productRepository.findById(id).get());
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    public StockDTO updateStock(Integer id, StockDTO stockDTO){
        try{
            Stock stock = stockRepo.findStockByProduct_Id(id);
            stock.setQuantity(stockDTO.getQuantity());
            stockRepo.save(stock);
            return stockDTO;
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDTO);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public CommonResponse getProductCount() {
        CommonResponse commonResponse = new CommonResponse();
        long count = productRepository.count();
        commonResponse.setMessage(Long.toString(count));
        return commonResponse;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepo.save(productMapper.categorytoEntity(categoryDTO));
        return productMapper.categorytoDTO(category);
    }

    @Override
    public SubCategoryDTO saveSubCategory(SubCategoryDTO subCategoryDTO) {
        SubCategory subCategory = subCategoryRepo.save(productMapper.subCategoryDTOtoSubcategory(subCategoryDTO));
        return productMapper.subCategorytoDTO(subCategory);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOList = productMapper.categorytoDTOList(categories);
        return categoryDTOList;}

    @Override
    public ProductDTO getProductByUuid(String uuid) {
        Product product = productRepository.findByUuid(uuid);
        return productMapper.toDTO(product);
    }

    @Override
    public List<SubCategoryDTO> getcategorySubByCategoryID(Integer categoryID) {
        try{
            List<SubCategory> subCategories = subCategoryRepo.findByCategory_Id(categoryID);
            return productMapper.subCategorytoDTOList(subCategories);
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public CategoryDTO getCategoryByID(Integer id) {
        try{
            Optional<Category> category = categoryRepo.findById(id);
            if(category.isPresent()){
                return productMapper.categorytoDTO(category.get());
            }
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
        return null;
    }

    public List<String> generateSKUs(SkuRequestDTO request) {
        if(request.getProductName().isEmpty()) {
            throw new ProductExeption("Product name is required");
        }
        if(request.getSpecifications().isEmpty()) {
            throw new ProductExeption("Specifications are required");
        }

        List<String> skus = new ArrayList<>();
        List<List<String>> attributeCombinations = new ArrayList<>();
        List<String> specNames = new ArrayList<>();

        for (SpecificationDTO spec : request.getSpecifications()) {
            attributeCombinations.add(spec.getAttributes());
            specNames.add(spec.getSpecification());
        }

        generateCombinations(request.getProductName(), attributeCombinations, specNames, 0, "", skus);
        return skus;
    }

    @Override
    public List<ProductDTO> searchProductByQuery(String query) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(query);
        return products.stream().map(productMapper::toDTO).toList();
    }

    @Override
    public Page<ProductDTO> getProductListByCategoryID(Integer categoryID, Pageable pageable) {
        try{
            Page<Product> products = productRepository.findByCategory(categoryID,pageable);
            return products.map(productMapper::toDTO);
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public Page<ProductDTO> getProductByCategoryIDSubcategory(Integer categoryID, Integer subCategoryID, Pageable pageable) {
        try{
            Page<Product> products = productRepository.findByCategoryAndSubCategory(categoryID,subCategoryID,pageable);
            return products.map(productMapper::toDTO);
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public void reviewProductBy(ReviewDTO reviewDTO) {
        try {
            Review review = productMapper.DTOtoReviewEntity(reviewDTO);
            for (ReviewImageData imageData : review.getImages()) {
                imageData.setReview(review);
            }
            reviewRepo.save(review);
            orderServiceImpl.reviewTrue(reviewDTO.getOrderId());
        } catch (ProductExeption e) {
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public List<ReviewDTO> getReviewProduct(Integer id) {
        try{
            List<Review> listReview = reviewRepo.findByProductId(id);
            return listReview.stream().map(productMapper::ReviewEntityToDTO).collect(Collectors.toList());
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public void deleteCategoryById(Integer id) {
        try{
            categoryRepo.deleteById(id);
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    @Override
    public void subCategoryDelete(Integer id) {
        try{
            subCategoryRepo.deleteById(id);
        }catch (ProductExeption e){
            throw new ProductExeption(e.getMessage());
        }
    }

    private void generateCombinations(String productName, List<List<String>> attributes, List<String> specNames, int index, String current, List<String> skus) {
        if (index == attributes.size()) {
            String uniqueCode = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            skus.add(productName.toUpperCase() + "-" + current + uniqueCode);
            return;
        }

        for (String attr : attributes.get(index)) {
            generateCombinations(productName, attributes, specNames, index + 1, current + specNames.get(index) + ":" + attr.toUpperCase() + "-", skus);
        }
    }
}
