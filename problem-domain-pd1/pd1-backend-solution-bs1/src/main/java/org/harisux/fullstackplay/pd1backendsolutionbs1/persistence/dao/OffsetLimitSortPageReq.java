package org.harisux.fullstackplay.pd1backendsolutionbs1.persistence.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OffsetLimitSortPageReq implements Pageable {

    private int offset;
    private int limit;
    private Sort sort;

    @Override public int getPageNumber() { return 0; }

    @Override public int getPageSize() { return limit; }

    @Override public long getOffset() { return offset; }

    @Override public Sort getSort() { return sort; }

    @Override public Pageable next() { return null; }

    @Override public Pageable previousOrFirst() { return this; }

    @Override public Pageable first() { return this; }

    @Override public Pageable withPage(int pageNumber) { return null; }

    @Override public boolean hasPrevious() { return false; }
    
}
