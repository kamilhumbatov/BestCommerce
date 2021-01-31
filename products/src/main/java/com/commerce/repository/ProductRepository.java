package com.commerce.repository;

import com.commerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdAndCreatedBy(long id, long createdAt);
    Page<Product> findAllByCreatedBy(long createdAt, Pageable pagable);
}
