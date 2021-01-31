package com.commerce.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePagedModel<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public ResponsePagedModel() {

    }

    public ResponsePagedModel(List<T> content, PageModel paged) {
        this.content = content;
        this.page = paged.getPage();
        this.size = paged.getSize();
        this.totalElements = paged.getTotalElements();
        this.totalPages = paged.getTotalPages();
        this.last = paged.isLast();
    }
}
