package com.qxcmp.framework.view.pagination;

import com.google.common.collect.Lists;
import com.qxcmp.framework.view.support.PaginationHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class PaginationHelperTest {

    @Test
    public void testSinglePage() throws Exception {
        Pagination pagination = getPagination(20, 0, 20);

        assertEquals(true, Objects.nonNull(pagination));

        assertEquals("/test?page=0&size=20&sort=", pagination.getFirst());
        assertEquals("/test?page=0&size=20&sort=", pagination.getPrevious());
        assertEquals("/test?page=0&size=20&sort=", pagination.getNext());
        assertEquals("/test?page=0&size=20&sort=", pagination.getLast());
        assertEquals(1, pagination.getCurrent());

        List<PaginationItem> paginationItems = pagination.getPaginationItems();
        assertEquals(1, paginationItems.size());
    }

    @Test
    public void testTwoPage() throws Exception {
        Pagination pagination = getPagination(21, 0, 20);

        assertEquals(true, Objects.nonNull(pagination));

        assertEquals("/test?page=0&size=20&sort=", pagination.getFirst());
        assertEquals("/test?page=0&size=20&sort=", pagination.getPrevious());
        assertEquals("/test?page=1&size=20&sort=", pagination.getNext());
        assertEquals("/test?page=1&size=20&sort=", pagination.getLast());
        assertEquals(1, pagination.getCurrent());

        List<PaginationItem> paginationItems = pagination.getPaginationItems();
        assertEquals(2, paginationItems.size());
    }

    @Test
    public void testMultiplePage() throws Exception {
        Pagination pagination = getPagination(101, 5, 10);
        assertEquals(true, Objects.nonNull(pagination));

        assertEquals("/test?page=0&size=10&sort=", pagination.getFirst());
        assertEquals("/test?page=4&size=10&sort=", pagination.getPrevious());
        assertEquals("/test?page=6&size=10&sort=", pagination.getNext());
        assertEquals("/test?page=10&size=10&sort=", pagination.getLast());
        assertEquals(6, pagination.getCurrent());

        List<PaginationItem> paginationItems = pagination.getPaginationItems();
        assertEquals(8, paginationItems.size());
    }

    private Pagination getPagination(int count, int page, int size) {
        List<String> items = Lists.newArrayList();

        for (int i = 0; i < count; i++) {
            items.add(RandomStringUtils.random(10));
        }

        return new PaginationHelper().next(new PageImpl<>(items, new PageRequest(page, size), items.size()), "/test");
    }
}