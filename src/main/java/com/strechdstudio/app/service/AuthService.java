package com.strechdstudio.app.service;

import com.strechdstudio.app.dto.*;
import com.strechdstudio.app.model.CodeLkup;
import com.strechdstudio.app.model.Codelist;
import com.strechdstudio.app.model.Customer;
import com.strechdstudio.app.model.User;
import com.strechdstudio.app.repository.CodeLkupRepository;
import com.strechdstudio.app.repository.CodelistRepository;
import com.strechdstudio.app.repository.CustomerRepository;
import com.strechdstudio.app.repository.UserRepository;
import com.strechdstudio.app.util.EmailValidator;
import com.strechdstudio.app.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CodelistRepository codelistRepository;

    @Autowired
    private CodeLkupRepository codeLkupRepository;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}") // Inject the secret key from the application.properties
    private String jwtSecret;

    // Convert the jwtSecret String into a SecretKey
    private Key getSigningKey() {
        // Ensure jwtSecret is Base64 encoded and at least 512 bits (64 bytes) long
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    // Authenticate and generate JWT Token
    public LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest) throws Exception {
        // Try to find the user by username or email
        User user = userRepository.findByUsernameIgnoreCase(loginRequest.getUsername())
                .orElseGet(() -> {
                    try {
                        return userRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                                .orElseThrow(() -> new Exception("User not found"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        // Check if the password matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        // Update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        String token = generateJwtToken(user);

        // Return both token and user details
        return new LoginResponse(token, user);
    }


    // Handle logout
    public void logout(UUID userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        // Update last logout timestamp
        user.setLastLogout(LocalDateTime.now());
        userRepository.save(user);
    }

    // Generate JWT token for authenticated user
    private String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use the converted SecretKey
                .compact();
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (!EmailValidator.isValidEmail(registerRequest.getEmail()))
            throw new RuntimeException("Please enter a valid email.");
        // Check if the user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken.");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken.");
        }

        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new RuntimeException("Phone number is already taken.");
        }

        LocalDateTime currentDate = LocalDateTime.now();
        // Create the user object
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        newUser.setActive(true); // Default to active on registration
        newUser.setLastLogin(null);
        newUser.setLastLogout(null);
        newUser.setAddDate(currentDate);
        newUser.setEditDate(currentDate);

        // Save the user in the database
        User savedUser = userRepository.save(newUser);

        // Create and associate a new Customer with the saved User
        Customer newCustomer = new Customer();
        newCustomer.setUser(savedUser); // Link customer to the user
        newCustomer.setFirstName(registerRequest.getFirstName());
        newCustomer.setLastName(registerRequest.getLastName());
        newCustomer.setEmail(registerRequest.getEmail());
        newCustomer.setPhoneNumber(registerRequest.getPhoneNumber());
        newCustomer.setPreferredContactMethod(" ");
        newCustomer.setCity("");
        newCustomer.setState("");
        newCustomer.setCountry("");
        newCustomer.setAddDate(currentDate);
        newCustomer.setEditDate(currentDate);
        newCustomer.setDateOfBirth(registerRequest.getDateOfBirth());
        newCustomer.setRegistrationDate(currentDate);
        newCustomer.setLastLoginDate(currentDate); // Set to null initially or use the current date
        newCustomer.setTotalClassesAttended(0); // Set to 0 initially

        // fetching codelist and codelkup values for the customer status
        Codelist customerStatusCodelist = codelistRepository.findBylistName("CUSTOMERSTATUS")
                .orElseThrow(() -> new RuntimeException("Codelist CUSTOMERSTATUS not found"));

        CodeLkup customerStatusCodelkup = codeLkupRepository.findByCodelist_ListNameAndCode(customerStatusCodelist.getListName(), "Active")
                .orElseThrow(() -> new RuntimeException("Codelkup status not found"));
        // set the codelkup value as default values to the customer
        newCustomer.setStatus(customerStatusCodelkup);

        // fetching codelist and codelkup values for the membership status
        Codelist membershipCodelist = codelistRepository.findBylistName("MEMBERSHIPTYPE")
                .orElseThrow(() -> new RuntimeException("Codelist MEMBERSHIPTYPE not found"));

        CodeLkup membershipCodelkup = codeLkupRepository.findByCodelist_ListNameAndCode(membershipCodelist.getListName(), "Regular")
                .orElseThrow(() -> new RuntimeException("Codelkup membership code not found"));
        // set the codelkup value as default values to the customer
        newCustomer.setMembershipType(membershipCodelkup);

        // Save the customer to the database
        customerRepository.save(newCustomer);

        // Generate a token
        String jwtToken = jwtUtil.generateToken(savedUser);


        // Prepare the RegisterResponse
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(savedUser.getEmail());
        registerResponse.setUsername(savedUser.getUsername());
        registerResponse.setPhoneNumber(savedUser.getPhoneNumber());
        registerResponse.setToken(jwtToken);
        registerResponse.setCustomerId(newCustomer.getCustomerId());

        return registerResponse;
    }

    public ValidateResponse checkUserExists(ValidateRequest validateRequest) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(validateRequest.getUsername())
                .or(() -> userRepository.findByEmailIgnoreCase(validateRequest.getUsername().toLowerCase()));

        if (user.isPresent()) {
            return new ValidateResponse(user.get().getEmail().toLowerCase(), true);
        } else {
            return new ValidateResponse(null, false);
        }
    }


}
