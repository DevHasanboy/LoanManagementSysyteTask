package com.example.file.task.utils;

import com.example.file.task.entity.User;
import com.example.file.task.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Util {
    private  final UserRepository userRepository;

    public Util(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Hozirgi foydalanuvchi (manager) ID sini olish
    public  User getCurrentManager() {
        // Authentication context dan foydalanuvchini olish
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userRepository.findByUsername(userDetails.getUsername()).get(); // Manager ID
        }
        return null; // Agar foydalanuvchi topilmasa
    }
}
