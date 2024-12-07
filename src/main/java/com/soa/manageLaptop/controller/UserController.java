package com.soa.manageLaptop.controller;

import com.soa.manageLaptop.model.User;
import com.soa.manageLaptop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users-order-count")
    public ResponseEntity<List<Map<String, Object>>> getUsersWithOrderCount() {
        List<Map<String, Object>> response = userService.findAll()
                .stream()
                .map(user -> {
                    Map<String, Object> userOrderData = new HashMap<>();
                    userOrderData.put("username", user.getUsername());
                    userOrderData.put("email", user.getEmail());
                    userOrderData.put("phone", user.getPhone());
                    userOrderData.put("orderCount", user.getOrders() != null ? user.getOrders().size() : 0);
                    return userOrderData;
                })
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{username}")
    public String findIdByUsername(@PathVariable String username) {
        return userService.getIdByUsername(username);
    }

    @GetMapping("/forgotPassword/{username}")
    public String forgotPassword(@PathVariable String username) {
        try{
            return userService.getEmailByUsername(username);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/updatePassword/{username}")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @RequestParam String password) {
        try{
            userService.updatePassword(username, password);
            return ResponseEntity.ok("Password updated successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

