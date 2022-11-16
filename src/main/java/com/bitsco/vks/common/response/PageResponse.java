package com.bitsco.vks.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List data;

    public PageResponse(PageImpl page) {
        this.pageNumber = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.data = page.getContent();
    }

    public PageResponse(Page page) {
        this.pageNumber = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.data = page.getContent();
    }
}
