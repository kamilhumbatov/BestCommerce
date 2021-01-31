package com.commerce.services.impl;

import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.common.models.User;
import com.commerce.dto.ProductDto;
import com.commerce.mapper.ProductMapper;
import com.commerce.models.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.services.ProductService;
import com.commerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    private Product findByIdAndUser(long id, User user) {
        return productRepository.findByIdAndCreatedBy(id, user.getId())
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductDto findById(String currentUser, long id) {
        if (currentUser != null) {
            User user = userService.findByUsernameOrEmail(currentUser);
            Product product = findByIdAndUser(id, user);
            return productMapper.sourceToDestination(product);
        }
        throw new UserNotFoundException();
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.destinationToSource(productDto);
        productRepository.save(product);
        return productMapper.sourceToDestination(product);
    }

    @Override
    public Page<ProductDto> allProducts(String currentUser, int page, int size, String sortBy) {
        User user = userService.findByUsernameOrEmail(currentUser);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAllByCreatedBy(user.getId(), pageable).map(
                product -> productMapper.sourceToDestination(product));
    }
}
