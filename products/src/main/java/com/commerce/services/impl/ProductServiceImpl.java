package com.commerce.services.impl;

import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.common.models.User;
import com.commerce.dto.ProductDto;
import com.commerce.mapper.ProductMapper;
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

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final UserService userService;

    private Product findByIdAndUser(long id, User user) {
        return productRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductDto findById(UserPrincipal currentUser, long id) {
        if (currentUser != null) {
            User user = userService.findByUsernameOrEmail(currentUser.getUsername());
            Product product = findByIdAndUser(id, user);
            return productMapper.sourceToDestination(product);
        }
        throw new UserNotFoundException();
    }

    @Override
    public ProductDto save(UserPrincipal currentUser, ProductDto productDto) {
        if (currentUser != null) {
            Product product = productMapper.destinationToSource(productDto);
            productRepository.save(product);
            return productMapper.sourceToDestination(product);
        }
        throw new UserNotFoundException();
    }

    @Override
    public ResponsePagedModel<Product> allProducts(UserPrincipal currentUser, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "price");
        Page<Product> products = productRepository.findAll(pageable);

        if (products.getNumberOfElements() == 0) {
            return new ResponsePagedModel<>(Collections.emptyList(), products);
        }

        List<Product> productResponse = products.map(product -> {
            return (product);
        }).getContent();
        return new ResponsePagedModel<Product>(productResponse, products);
    }
}
