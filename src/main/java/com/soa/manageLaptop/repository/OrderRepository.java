package com.soa.manageLaptop.repository;

import com.soa.manageLaptop.model.Order;
import com.soa.manageLaptop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
