package com.commerce.services;

import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.dto.ProductDto;
import com.commerce.models.Product;

public interface ProductService {
    ProductDto findById(String currentUser, long id);
    ProductDto save(ProductDto product);
    ResponsePagedModel<Product> allProducts(String currentUser, int page, int size);
}
