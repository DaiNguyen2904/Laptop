package com.soa.manageLaptop.repository;

import com.soa.manageLaptop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String keyword);
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByIdIn(List<Long> ids);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    Page<Product> findByNameContainingAndPriceBetween(String name, double minPrice, double maxPrice, Pageable pageable);
}