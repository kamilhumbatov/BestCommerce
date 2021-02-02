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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class ProductRestControllerTest {

    private static final Long PRODUCT_ID = 100L;
    private static final String PAGE_SORT_SIZE = "5";
    private static final String PAGE_NUMBER = "page";
    private static final String PAGE_NUMBER_VALUE = "1";
    private static final String PAGE_SIZE = "size";
    private static final String PAGE_SORT = "sort";
    private static final String ERROR_CODE = "$.status";
    private static final String ERROR_MESSAGE = "message";

    private static final String API_GET = "/product/{id}";
    private static final String API_SAVE = "/product/save";
    private static final String API_LIST = "/product/list";

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
                .name("Apple")
                .description("")
                .deliveryOptions("")
                .category(ProductCategory.FASHION)
                .build();
        productAddDto = ProductDto.builder()
                .paymentOptions(PaymentOptions.DIRECT)
                .price(BigDecimal.TEN)
                .inventory("")
                .name("Apple")
                .description("")
                .deliveryOptions("")
                .category(ProductCategory.FASHION)
                .build();
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void findProductById() throws Exception {
        when(productService.findById(PRODUCT_ID)).thenReturn(productDto);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id",is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.price",is(10)));
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void findProductByIdReturnsNotFound() throws Exception {
        Long FACE_PRODUCT_ID=12345L;
        when(productService.findById(FACE_PRODUCT_ID)).thenThrow(new ProductNotFoundException());

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
        Page<ProductDto> expectedProduct = new PageImpl<>(Collections.singletonList(productDto));
        when(productService.allProducts(
                Integer.parseInt(PAGE_NUMBER_VALUE),
                Integer.parseInt(PAGE_SORT_SIZE),"price")).thenReturn(expectedProduct);

        mockMvc.perform(MockMvcRequestBuilders
                .get(API_LIST)
                .param(PAGE_NUMBER, PAGE_NUMBER_VALUE)
                .param(PAGE_SIZE, PAGE_SORT_SIZE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.totalElements", is(1)))
                .andExpect(jsonPath("$.result.totalPages", is(1)));
    }

    @Test
    @WithUserDetails("user@bestcommerce.com")
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete(API_GET, 1))
                .andExpect(status().isOk());
    }


    private String asJsonString(final Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
