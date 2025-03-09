//package com.strechdstudio.app.service;
//
//import com.strechdstudio.app.model.Customer;
//import com.strechdstudio.app.model.Role;
//import com.strechdstudio.app.model.User;
//import com.strechdstudio.app.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public User createUser(UserDTO userDTO) {
//        // Validate inputs (username, password, etc.)
//
//        // Check if username already exists
//        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
//        if (existingUser.isPresent()) {
//            throw new IllegalArgumentException("Username already exists");
//        }
//
//        // Hash password
//        String hashedPassword = hashPassword(userDTO.getPassword());
//
//        // Create User
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(hashedPassword);
//        user.setRole(userDTO.getRole());  // Set role (ADMIN, CUSTOMER, INSTRUCTOR)
//
//        // If it's a customer user, associate them with a customer record
//        if (user.getRole() == Role.CUSTOMER) {
//            Optional<Customer> customer = customerRepository.findByEmail(userDTO.getUsername());
//            user.setCustomer(customer.orElseThrow(() -> new IllegalArgumentException("Customer not found")));
//        }
//
//        // Save user
//        return userRepository.save(user);
//    }
//
////    private String hashPassword(String password) {
////        // Use a secure hashing function such as bcrypt or Argon2
////        return new BCryptPasswordEncoder().encode(password);
////    }
//}
