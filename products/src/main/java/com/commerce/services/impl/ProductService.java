package com.commerce.services.impl;

import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import com.commerce.security.UserPrincipal;

public interface ProductService {
    ProductDto findById(UserPrincipal currentUser, long id);
    ProductDto save(UserPrincipal currentUser, ProductDto product);
    ResponsePagedModel<Product> allProducts(UserPrincipal currentUser, int page, int size);
}
