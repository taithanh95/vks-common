package com.bitsco.vks.common.util;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: truongnq
 * @date: 22-Jan-19 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class PageCommon {

    public static Pageable createPageable(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "UNKNOW");
        return PageRequest.of(page - 1, size);
    }

    public static PageResponse toPageResponse(List list, int page, int size) {
        int listSize = 0;
        if (ArrayListCommon.isNullOrEmpty(list))
            throw new CommonException(Response.DATA_NOT_FOUND);
        listSize = list.size();
        if (page < 1)
            page = 1;
        if (size < 1)
            size = listSize;

        Pageable pageable = createPageable(page, size);
        if (pageable.getOffset() >= listSize)
            return new PageResponse(new PageImpl(list, pageable, listSize));
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > listSize ?
                listSize :
                pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageResponse(new PageImpl(subList, pageable, listSize));
    }

    public static Pageable createPageable(com.bitsco.vks.common.request.PageRequest pageRequest) {
        Sort sort = null;
        if (!StringCommon.isNullOrBlank(pageRequest.getSortField()))
            sort = Sort.by(
                    StringCommon.isNullOrBlank(pageRequest.getSortField()) || pageRequest.getSortField().trim().toUpperCase().equals(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC,
                    pageRequest.getSortField().trim()
            );
        if (sort == null)
            return PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize());
        else
            return PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), sort);
    }

    public static PageResponse toPageResponse(List list, com.bitsco.vks.common.request.PageRequest pageRequest) {
        int page = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        int listSize = 0;
        if (ArrayListCommon.isNullOrEmpty(list))
            throw new CommonException(Response.DATA_NOT_FOUND);
        listSize = list.size();
        if (page < 1)
            page = 1;
        if (size < 1)
            size = listSize;

        Pageable pageable = createPageable(pageRequest);
        if (pageable.getOffset() >= listSize)
            return new PageResponse(new PageImpl(list, pageable, listSize));
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > listSize ?
                listSize :
                pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageResponse(new PageImpl(subList, pageable, listSize));
    }
}
