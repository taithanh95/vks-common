package com.bitsco.vks.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    private int pageNumber;
    private int pageSize;
    private String sortField;
    private String sortOrder;
    private Object dataRequest;
}
