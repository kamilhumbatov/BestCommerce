package com.commerce.mapper;

import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto sourceToDestination(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .category(product.getCategory())
                .deliveryOptions(product.getDeliveryOptions())
                .description(product.getDescription())
                .inventory(product.getInventory())
                .name(product.getName())
                .paymentOptions(product.getPaymentOptions())
                .price(product.getPrice())
                .build();
        return productDto;
    }

    @Override
    public Product destinationToSource(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = Product.builder()
                .id(productDto.getId())
                .category(productDto.getCategory())
                .deliveryOptions(productDto.getDeliveryOptions())
                .description(productDto.getDescription())
                .inventory(productDto.getInventory())
                .name(productDto.getName())
                .paymentOptions(productDto.getPaymentOptions())
                .price(productDto.getPrice())
                .build();
        return product;
    }
}
