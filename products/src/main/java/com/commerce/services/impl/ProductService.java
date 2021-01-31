package com.commerce.services.impl;

import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.models.Product;
import com.commerce.security.UserPrincipal;

public interface ProductService {
    Product findById(UserPrincipal currentUser, long id);
    Product save(UserPrincipal currentUser,Product product);
    ResponsePagedModel<Product> allProducts(UserPrincipal currentUser, int page, int size);
}
