package com.soa.manageLaptop.model;

import com.soa.manageLaptop.model._enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String password;
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private List<Order> orders;



    public User(String johnDoe, String mail, String number, String password123) {
    }

}