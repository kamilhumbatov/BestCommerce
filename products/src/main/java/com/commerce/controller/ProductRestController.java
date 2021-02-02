package com.commerce.controller;

import com.commerce.common.dto.ResponseModel;
import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import com.commerce.services.ProductService;
import com.commerce.util.Auditor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Api("Product API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "30";
    private final String DEFAULT_PAGE_SORT = "price";
    private static final String PAGE_SIZE_MUST_BE_POSITIVE = "Page size must be positive";
    private static final String PAGE_INDEX_MUST_BE_POSITIVE = "Page index must be positive";
    private static final String ID_MUST_BE_POSITIVE = "Id must be positive";

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ResponseModel<Product> findById(
            @PathVariable @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        return ResponseModel.ok(productService.findById(id));
    }

    @PostMapping("/save")
    @ApiOperation("Add new product")
    public ResponseModel<ProductDto> save(
            @Validated @RequestBody ProductDto product) {
        return ResponseModel.ok(productService.save(product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete product by id")
    public void deleteProduct(
            @PathVariable @Positive(message = ID_MUST_BE_POSITIVE) long id) {
        productService.deleteById(id);
    }

    @GetMapping("/list")
    @ApiOperation("Find all product by page")
    public ResponseModel<Page<ProductDto>> allProducts(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER)
            @PositiveOrZero(message = PAGE_INDEX_MUST_BE_POSITIVE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE)
            @Positive(message = PAGE_SIZE_MUST_BE_POSITIVE) int size,
            @RequestParam(value = "sort", defaultValue = DEFAULT_PAGE_SORT) String sort) {
        return ResponseModel.ok(productService.allProducts(page, size, sort));
    }
}
