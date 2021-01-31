package com.commerce.mapper;

import com.commerce.dto.ProductDto;
import com.commerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductDto sourceToDestination(Product product);
    Product destinationToSource(ProductDto productDto);
}
