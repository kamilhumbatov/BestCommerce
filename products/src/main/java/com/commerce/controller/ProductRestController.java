package com.commerce.controller;


import com.commerce.common.dto.ResponseModel;
import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import com.commerce.services.ProductService;
import com.commerce.util.Auditor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "30";
    private final String DEFAULT_PAGE_SORT = "price";

    @GetMapping("/{id}")
    public ResponseModel<Product> findById(
            @PathVariable long id) {
        return ResponseModel.ok(productService.findById(id));
    }

    @PostMapping("/save")
    public ResponseModel<ProductDto> save(
            @RequestBody ProductDto product) {
        return ResponseModel.ok(productService.save(product));
    }

    @GetMapping("/list")
    public ResponseModel<Page<ProductDto>> allProducts(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sort", defaultValue = DEFAULT_PAGE_SORT) String sort) {
        return ResponseModel.ok(productService.allProducts(page, size, sort));
    }
}
