package com.commerce.services.impl;

import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.common.models.User;
import com.commerce.models.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public Product findById(UserPrincipal currentUser, long id) {
        if (currentUser != null) {
            User user = userService.findByUsernameOrEmail(currentUser.getUsername());
            return productRepository.findByIdAndUser(id, user).orElseThrow(ProductNotFoundException::new);
        }
        throw new UserNotFoundException();
    }

    public Product save(UserPrincipal currentUser,Product product) {
        if (currentUser != null) {
            User user = userService.findByUsernameOrEmail(currentUser.getUsername());
            product.setUser(user);
            return productRepository.save(product);
        }
        throw new UserNotFoundException();
    }

    public ResponsePagedModel<Product> allProducts(UserPrincipal currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "price");
        Page<Product> products = productRepository.findAll(pageable);

        if (products.getNumberOfElements() == 0) {
            return new ResponsePagedModel<>(Collections.emptyList(), products);
        }

        List<Product> pollResponses = products.map(product -> { return product;}).getContent();
        return new ResponsePagedModel<>(pollResponses, products);
    }
}
