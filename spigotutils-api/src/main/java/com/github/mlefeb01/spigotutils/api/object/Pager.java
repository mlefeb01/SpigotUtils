package com.github.mlefeb01.spigotutils.api.object;


import java.util.List;

/**
 * Simple pagination object
 *
 * @param <T> type
 * @author Matt Lefebvre
 */
public class Pager<T> {
    private final List<T> list;
    private final int pageSize;
    private final int maxPage;

    /**
     * Constructor
     *
     * @param list     list
     * @param pageSize pageSize
     */
    public Pager(List<T> list, int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalStateException(String.format("pageSize must be greater than 0, actual %d", pageSize));
        }
        this.list = list;
        this.pageSize = pageSize;
        this.maxPage = (int) Math.ceil((double) list.size() / pageSize);
    }

    /**
     * Checks if a page is valid
     *
     * @param page page
     * @return page
     */
    public boolean isPage(int page) {
        return page >= 1 && page <= maxPage;
    }

    /**
     * Returns the max page
     *
     * @return maxPage
     */
    public int getMaxPage() {
        return maxPage;
    }

    /**
     * Gets a page
     *
     * @param page page
     * @return page
     */
    public List<T> getPage(int page) {
        final int start = (page - 1) * pageSize;
        final int end = Math.min(start + pageSize, list.size());
        return list.subList(start, end);
    }

}
