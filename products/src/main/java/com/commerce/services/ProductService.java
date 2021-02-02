package com.commerce.services;

import com.commerce.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDto findById(long id);
    ProductDto save(ProductDto product);
    void deleteById(long id);
    Page<ProductDto> allProducts(int page, int size, String sortBy);
}
