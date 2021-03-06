package com.commerce.services.impl;

import com.commerce.common.exception.models.AccessDinedProductException;
import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.dto.ProductDto;
import com.commerce.mapper.ProductMapper;
import com.commerce.models.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.services.ProductService;
import com.commerce.util.Auditor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_CACHE = "productCache";

    private final Auditor auditor;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Cacheable(value = PRODUCT_CACHE, key = "#id")
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    private Product findByIdAndUser(Long id, String user) {
        Product product = findById(id);
        if (!product.getCreatedBy().equals(user)) {
            throw new AccessDinedProductException();
        }
        return product;
    }

    @Override
    public ProductDto findById(long id) {
        String currentUser = auditor.getUserName();
        if (currentUser != null) {
            Product product = findByIdAndUser(id, currentUser);
            return productMapper.sourceToDestination(product);
        }
        throw new UserNotFoundException();
    }

    @Override
    @CachePut(value = PRODUCT_CACHE, key = "#id", condition = "#result != null")
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.destinationToSource(productDto);
        productRepository.save(product);
        return productMapper.sourceToDestination(product);
    }

    @Override
    @CacheEvict(value = PRODUCT_CACHE, key = "#id")
    public void deleteById(long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductDto> allProducts(int page, int size, String sortBy) {
        String currentUser = auditor.getUserName();
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAllByCreatedBy(currentUser, pageable).map(
                product -> productMapper.sourceToDestination(product));
    }
}
