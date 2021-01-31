package com.commerce.controller;

import com.commerce.common.exception.models.ProductNotFoundException;
import com.commerce.dto.ProductDto;
import com.commerce.services.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductRestController.class)
@ContextConfiguration(classes = {ProductRestController.class})
public class ProductRestControllerTest {

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

    private ProductDto productDto;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        productDto = ProductDto.builder().id(PRODUCT_ID).build();
    }

    @Test
    public void findProductById() throws Exception {
        when(productService.findById(USERNAME,PRODUCT_ID)).thenReturn(productDto);

        mockMvc.perform(get(API_GET, PRODUCT_ID)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void findProductByIdReturnsNotFound() throws Exception {
        when(productService.findById(USERNAME,PRODUCT_ID)).thenThrow(new ProductNotFoundException());

        mockMvc.perform(get("/product/12345")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(ERROR_CODE, is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath(ERROR_MESSAGE, CoreMatchers.is("Product not found!")));
    }

    @Test
    public void retrieveAllProductCheckPageNumberBadRequest() throws Exception {
        mockMvc.perform(get(PAGE_URL)
                .param(PAGE_NUMBER, "-1")
                .param(PAGE_SIZE, PAGE_SORT_SIZE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_CODE, is(HttpStatus.BAD_REQUEST.value())));
    }

}
