package com.soa.manageLaptop.service;

import com.soa.manageLaptop.model.User;
import com.soa.manageLaptop.model._enum.Role;
import com.soa.manageLaptop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(User user) {
        if(!checkOverlap(user.getUsername(), user.getEmail(), user.getPhone())){
            throw new RuntimeException("User already exists");
        }
        user.setRole(Role.USER);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean checkOverlap(String userName, String email, String phone){
        return !userRepository.existsByEmailOrUsernameOrPhone(email, userName, phone);
    }

    public String getIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        User userObj = user.get();
        return String.valueOf(userObj.getId());
    }

    public String getEmailByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User userObj = user.get();
        return userObj.getEmail();
    }

    public User updatePassword(String username, String newPassword) {
        User user = userRepository.getUserByUsername(username);

        // Mã hóa mật khẩu mới
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(newPassword); // Mã hóa mật khẩu mới

        boolean check = passwordEncoder.matches(newPassword, encodedPassword);
        System.out.println(newPassword + ' ' + encodedPassword);
        System.out.println(check);
        // Cập nhật mật khẩu đã mã hóa cho người dùng
        user.setPassword(encodedPassword);

        // Lưu người dùng với mật khẩu mới đã mã hóa vào cơ sở dữ liệu
        return userRepository.save(user);
    }
}

