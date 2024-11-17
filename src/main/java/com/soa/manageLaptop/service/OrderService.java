package com.soa.manageLaptop.service;

import com.soa.manageLaptop.model.Order;
import com.soa.manageLaptop.model.Product;
import com.soa.manageLaptop.model.User;
import com.soa.manageLaptop.repository.OrderRepository;
import com.soa.manageLaptop.repository.ProductRepository;
import com.soa.manageLaptop.repository.UserRepository;
import com.soa.manageLaptop.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository; // Repository để truy cập thông tin sản phẩm

    public Order createOrder(OrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user); // This sets the user, which should link to the user_id in the database
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double totalPrice = 0;

        for (Long productId : orderRequest.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            order.getProductIds().add(productId);
            totalPrice += product.getPrice();
        }

        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }


    public List<Order> getOrdersByUser(Long id) {
        User user = userRepository.getById(id);
        return orderRepository.findByUser(user);
    }

    public Map<String, Long> getOrdersCountByMonth() {
        List<Order> orders = orderRepository.findAll();
        Map<String, Long> ordersCountByMonth = new HashMap<>();

        for (Order order : orders) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            String monthYear = orderDate.getMonth().toString() + " " + orderDate.getYear();
            ordersCountByMonth.put(monthYear, ordersCountByMonth.getOrDefault(monthYear, 0L) + 1);
        }

        return ordersCountByMonth;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Map<String, Integer> getOrdersCountByDay(int month, int year) {
        List<Order> orders = orderRepository.findAll(); // Lấy tất cả đơn hàng (có thể thêm lọc theo tháng và năm)

        Map<String, Integer> dailyCounts = new HashMap<>();
        for (Order order : orders) {
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            if (orderDate.getMonthValue() == month && orderDate.getYear() == year) {
                String dayKey = orderDate.toString();  // Chuyển thành String "yyyy-MM-dd"
                dailyCounts.put(dayKey, dailyCounts.getOrDefault(dayKey, 0) + 1);
            }
        }

        return dailyCounts;
    }
}

