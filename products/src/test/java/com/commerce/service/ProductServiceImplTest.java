package com.commerce.service;

import com.commerce.common.exception.models.UserNotFoundException;
import com.commerce.common.models.User;
import com.commerce.dto.ProductDto;
import com.commerce.mapper.ProductMapper;
import com.commerce.models.Product;
import com.commerce.repository.ProductRepository;
import com.commerce.services.UserService;
import com.commerce.services.impl.ProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private static final Long USER_ID = 5L;
    private static final Long PRODUCT_ID = 100L;
    private static final int PAGE_SORT_NUMBER = 1;
    private static final int PAGE_SORT_SIZE = 5;


    private User user;
    private Product product;
    private ProductDto productDto;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Before
    public void setUp() {
        user = User.builder().id(USER_ID).username(USERNAME).email(USERNAME).build();
        productDto = ProductDto.builder().id(PRODUCT_ID).build();
        product = Product.builder().id(PRODUCT_ID).build();
    }

    @Test
    public void givenIdAndUser() {
        when(userService.findByUsernameOrEmail(USERNAME)).thenReturn(user);
        when(productRepository.findByIdAndCreatedBy(PRODUCT_ID, USER_ID)).thenReturn(Optional.of(product));
        when(productMapper.sourceToDestination(product)).thenReturn(productDto);

        assertThat(productService.findById(USERNAME, PRODUCT_ID).getId()).isEqualTo(PRODUCT_ID);
        verify(productRepository).findByIdAndCreatedBy(PRODUCT_ID, USER_ID);
    }

    @Test
    public void givenIdUserNotFoundException() {
        assertThatThrownBy(() -> productService.findById(null, PRODUCT_ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found!");
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

        when(userService.findByUsernameOrEmail(USERNAME)).thenReturn(user);
        when(productRepository.findAllByCreatedBy(USER_ID, pageRequest)).thenReturn(expectedProduct);

        Page<ProductDto> expectedProductDto =productService.allProducts(USERNAME, PAGE_SORT_NUMBER, PAGE_SORT_SIZE, "price");
        assertThat(expectedProductDto.getContent()).isEqualTo(expectedProductDto.getContent());
        assertThat(expectedProductDto.getTotalElements()).isEqualTo(1);
        assertThat(expectedProductDto.getTotalPages()).isEqualTo(1);

        verify(productRepository).findAllByCreatedBy(USER_ID, pageRequest);
    }

}
