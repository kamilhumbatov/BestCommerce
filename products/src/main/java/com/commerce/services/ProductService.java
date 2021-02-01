package com.commerce.services;

import com.commerce.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto findById(Long currentUser, long id);
    ProductDto save(ProductDto product);
    Page<ProductDto> allProducts(Long currentUser, int page, int size, String sortBy);
}
