package com.commerce.controller;

import com.commerce.services.ProductService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductRestController.class)
public class ProductRestControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void evaluatesPageableParameter() throws Exception {

//        mockMvc.perform(get("/characters/page")
//                .param("page", "5")
//                .param("size", "10")
//                .param("sort", "id,desc")   // <-- no space after comma!
//                .param("sort", "name,asc")) // <-- no space after comma!
//                .andExpect(status().isOk());
//
//        ArgumentCaptor<Pageable> pageableCaptor =
//                ArgumentCaptor.forClass(Pageable.class);
//        verify(productService).findAllPage(pageableCaptor.capture());
//        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
//
//        assertThat(pageable).hasPageNumber(5);
//        assertThat(pageable).hasPageSize(10);
//        assertThat(pageable).hasSort("name", Sort.Direction.ASC);
//        assertThat(pageable).hasSort("id", Sort.Direction.DESC);
    }
}
