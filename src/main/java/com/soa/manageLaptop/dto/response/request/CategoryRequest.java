package com.soa.manageLaptop.dto.response.request;

public class CategoryRequest {
    private String name;
    private long[] ids;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }
}

