package com.commerce.services;

import com.commerce.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto findById(String currentUser, long id);
    ProductDto save(ProductDto product);
    Page<ProductDto> allProducts(String currentUser, int page, int size, String sortBy);
}
