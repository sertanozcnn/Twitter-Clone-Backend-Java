package com.sertann.controller;


import com.sertann.config.JwtProvider;
import com.sertann.config.RandomColorPicker;
import com.sertann.models.User;
import com.sertann.repository.UserRepository;
import com.sertann.request.LoginRequest;
import com.sertann.response.AuthResponse;
import com.sertann.service.CustomerUserDetailsService;
import com.sertann.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    //  /auth/signup
    @PostMapping("/signup") //User Register
    public AuthResponse createUser(@RequestBody User user) throws Exception {

        User isExist = userRepository.findByEmail(user.getEmail());
        if (isExist != null) {
            throw new Exception("Email is already used with another account.");
        }

        //User Registration
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setGender(user.getGender());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); //We have stored the password securely
        String randomColor = RandomColorPicker.getRandomColor();
        newUser.setRandomProfileColorCode(randomColor);
        newUser.setImage(user.getImage());
        newUser.setBackgroundImage(user.getBackgroundImage());
        newUser.setNickname(user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase());

        User savedUser = userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Register Successfully");
    }

    //auth /auth/signin
    @PostMapping("/signin") //User Login
    public AuthResponse signIn(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "Login Successfully");

    }


    @PostMapping("/logout") //User Logout
    public ResponseEntity<String> logout() {

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout Successfully");
    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password not matched");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


    }

}
