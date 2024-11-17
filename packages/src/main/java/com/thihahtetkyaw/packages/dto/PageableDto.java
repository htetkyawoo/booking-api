package com.thihahtetkyaw.packages.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableDto<T>{

    private long currentPage;
    private boolean first;
    private boolean last;
    private long totalPages;
    private long totalElements;
    private long size;
    private List<T> data;

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage + 1;
    }
}
