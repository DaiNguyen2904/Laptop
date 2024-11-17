package com.soa.manageLaptop.request;

import java.util.List;

public class OrderRequest {
    private Long userId;
    private List<Long> productIds; // Danh sách ID sản phẩm

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
