package com.commerce.controller;

import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.dto.ProductDto;
import com.commerce.enums.PaymentOptions;
import com.commerce.enums.ProductCategory;
import com.commerce.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class ProductRestControllerTest {

    private static final Long USER_ID = 5L;
    private static final String USERNAME = "kamil";
    private static final Long PRODUCT_ID = 100L;
    private static final String PAGE_SORT_SIZE = "5";
    private static final String PAGE_URL = "/product/list";
    private static final String PAGE_NUMBER = "page";
    private static final String PAGE_SIZE = "size";
    private static final String PAGE_SORT = "sort";
    private static final String ERROR_CODE = "$.status";
    private static final String ERROR_MESSAGE = "message";

    private static final String API_GET = "/product/{id}";
    private static final String API_SAVE = "/product/save";

    private ProductDto productDto;
    private ProductDto productAddDto;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        productDto = ProductDto.builder()
                .id(PRODUCT_ID)
                .paymentOptions(PaymentOptions.DIRECT)
                .price(BigDecimal.TEN)
                .inventory("")
                .name("")
                .description("")
                .deliveryOptions("")
                .category(ProductCategory.FASHION)
                .build();
        productAddDto = ProductDto.builder()
                .paymentOptions(PaymentOptions.DIRECT)
                .price(BigDecimal.TEN)
                .inventory("")
                .name("")
                .description("")
                .deliveryOptions("")
                .category(ProductCategory.FASHION)
                .build();
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void findProductById() throws Exception {
        when(productService.findById(USER_ID,PRODUCT_ID)).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders
                .get(API_GET, PRODUCT_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void createProduct() throws Exception {
        when(productService.save(productAddDto)).thenReturn(productDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post(API_SAVE)
                .content(asJsonString(productAddDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists());
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void findProductByIdReturnsNotFound() throws Exception {
        when(productService.findById(USER_ID,PRODUCT_ID)).thenThrow(new ProductNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/12345")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_CODE, is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath(ERROR_MESSAGE, CoreMatchers.is("Product not found!")));
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void retrieveAllProductCheckPageNumberBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PAGE_URL)
                .param(PAGE_NUMBER, "-1")
                .param(PAGE_SIZE, PAGE_SORT_SIZE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_CODE, is(HttpStatus.BAD_REQUEST.value())));
    }

    private String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
