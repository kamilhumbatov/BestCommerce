package com.commerce.service;

import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.dto.ProductDto;
import com.commerce.mapper.ProductMapper;
import com.commerce.models.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.services.impl.ProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.util.Auditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductServiceImpl.class)
@ContextConfiguration(classes = {ProductServiceImpl.class})
public class ProductServiceImplTest {

    private static final String USERNAME = "kamil";
    private static final Long PRODUCT_ID = 100L;
    private static final int PAGE_SORT_NUMBER = 1;
    private static final int PAGE_SORT_SIZE = 5;

    private Product product;
    private ProductDto productDto;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private Auditor auditor;

    @Autowired
    private ProductServiceImpl productService;

    @Before
    public void setUp() {
        productDto = ProductDto.builder().id(PRODUCT_ID).build();
        product = Product.builder().id(PRODUCT_ID).build();
    }

    @Test
    public void givenIdAndUser() {
        when(productRepository.findByIdAndCreatedBy(PRODUCT_ID, USERNAME)).thenReturn(Optional.of(product));
        when(productMapper.sourceToDestination(product)).thenReturn(productDto);
        when(auditor.getUserName()).thenReturn(USERNAME);

        assertThat(productService.findById(PRODUCT_ID).getId()).isEqualTo(PRODUCT_ID);
        verify(productRepository).findByIdAndCreatedBy(PRODUCT_ID, USERNAME);
    }

    @Test
    public void givenIdUserNotFoundException() {
        when(auditor.getUserName()).thenReturn(null);

        assertThatThrownBy(() -> productService.findById(PRODUCT_ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found!");
        verify(auditor,times(1)).getUserName();
    }

    @Test
    public void save() {
        when(productMapper.destinationToSource(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.sourceToDestination(product)).thenReturn(productDto);

        assertThat(productService.save(productDto).getId()).isEqualTo(PRODUCT_ID);
        verify(productRepository).save(product);
    }

    @Test
    public void findAllPaginationAsc() {
        PageRequest pageRequest = PageRequest.of(PAGE_SORT_NUMBER, PAGE_SORT_SIZE, Sort.by(Sort.Order.asc("price")));
        Page<Product> expectedProduct = new PageImpl<>(Collections.singletonList(product));

        when(productRepository.findAllByCreatedBy(USERNAME, pageRequest)).thenReturn(expectedProduct);
        when(auditor.getUserName()).thenReturn(USERNAME);

        Page<ProductDto> expectedProductDto = productService.allProducts(PAGE_SORT_NUMBER, PAGE_SORT_SIZE, "price");
        assertThat(expectedProductDto.getContent()).isEqualTo(expectedProductDto.getContent());
        assertThat(expectedProductDto.getTotalElements()).isEqualTo(1);
        assertThat(expectedProductDto.getTotalPages()).isEqualTo(1);

        verify(productRepository).findAllByCreatedBy(USERNAME, pageRequest);
    }

    @Test
    public void deleteById(){
        when(auditor.getUserName()).thenReturn(USERNAME);
        when(productRepository.findByIdAndCreatedBy(PRODUCT_ID, USERNAME)).thenReturn(Optional.of(product));

        productService.deleteById(PRODUCT_ID);
        verify(productRepository).deleteById(PRODUCT_ID);
    }

}
