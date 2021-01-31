package com.commerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
