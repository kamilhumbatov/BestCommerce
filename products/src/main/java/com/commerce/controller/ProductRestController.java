package com.commerce.controller;


import com.commerce.common.dto.ResponseModel;
import com.commerce.common.dto.ResponsePagedModel;
import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import com.commerce.security.CurrentUser;
import com.commerce.security.UserPrincipal;
import com.commerce.services.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductRestController {

    private final ProductService productService;

    private final String DEFAULT_PAGE_NUMBER = "0";
    private final String DEFAULT_PAGE_SIZE = "30";

    @GetMapping("/{id}")
    public ResponseModel<Product> findById(
            @CurrentUser UserPrincipal currentUser,
            @PathVariable long id) {
        return ResponseModel.ok(productService.findById(currentUser, id));
    }

    @PostMapping("/save")
    public ResponseModel<ProductDto> save(
            @CurrentUser UserPrincipal currentUser,
            @RequestBody ProductDto product) {
        return ResponseModel.ok(productService.save(currentUser, product));
    }

    @GetMapping("/products")
    public ResponseModel<ResponsePagedModel<Product> >allProducts(
            @CurrentUser UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return ResponseModel.ok(productService.allProducts(currentUser, page, size));
    }
}
