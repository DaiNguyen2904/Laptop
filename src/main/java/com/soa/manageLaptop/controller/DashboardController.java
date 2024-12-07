package com.soa.manageLaptop.controller;

import com.soa.manageLaptop.service.OrderService;
import com.soa.manageLaptop.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // Lấy số lượng đơn hàng theo tháng
        Map<String, Long> ordersCountByMonth = orderService.getOrdersCountByMonth();
        model.addAttribute("ordersCountByMonth", ordersCountByMonth);

        // Lấy danh sách đơn hàng
        List<Order> orders = orderService.getOrdersByUser(1L); // Giả sử lấy theo user_id = 1
        model.addAttribute("orders", orders);

        return "dashboard"; // Trả về trang dashboard.html
    }

    @GetMapping("/dashboard/categories")
    public String getCategories(Model model) {
        return "category";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "HomeShop";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/log-out")
    public String logout(Model model) {
        return "logout";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        return "checkout";
    }

    @GetMapping("/payment-failure")
    public String paymentFailure(Model model) {
        return "payment-failure";
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(Model model) {
        return "payment-success";
    }

    @GetMapping("/myOrder")
    public String myOrder(Model model) {
        return "myOrder";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        return "forgetPassword";
    }

    @GetMapping("/productDetail")
    public String productDetails(Model model) {
        return "productDetail";
    }
}
