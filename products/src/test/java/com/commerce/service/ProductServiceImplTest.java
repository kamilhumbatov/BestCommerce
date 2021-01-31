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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductServiceImpl.class)
@ContextConfiguration(classes = {ProductServiceImpl.class})
public class ProductServiceImplTest {

    private static final String username="kamil";
    private static final Long userId=5L;
    private static final Long productId=100L;

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
    public void setUp(){
        user=User.builder().id(userId).username(username).email(username).build();
        productDto=ProductDto.builder().id(productId).build();
        product=Product.builder().id(productId).build();
    }

    @Test
    public void givenIdAndUser(){
        when(userService.findByUsernameOrEmail(username)).thenReturn(user);
        when(productRepository.findByIdAndCreatedBy(productId,userId)).thenReturn(Optional.of(product));
        when(productMapper.sourceToDestination(product)).thenReturn(productDto);

        assertThat(productService.findById(username,productId).getId()).isEqualTo(productId);
        verify(productRepository).findByIdAndCreatedBy(productId,userId);
    }

    @Test
    public void givenIdUserNotFoundException(){
        assertThatThrownBy(()->productService.findById(null,productId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found!");
    }
}
