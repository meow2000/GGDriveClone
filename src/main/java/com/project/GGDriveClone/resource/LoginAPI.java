package com.project.GGDriveClone.resource;

import com.project.GGDriveClone.DTO.LoginRequest;
import com.project.GGDriveClone.entity.PlanEntity;
import com.project.GGDriveClone.entity.UserEntity;
import com.project.GGDriveClone.jwt.JwtTokenProvider;
import com.project.GGDriveClone.repository.UserRepository;
import com.project.GGDriveClone.security.CustomAuthenticationProvider;
import com.project.GGDriveClone.security.CustomUserDetails;
import com.project.GGDriveClone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class LoginAPI {
    @Autowired
    private CustomAuthenticationProvider authenticationManager;

    @Autowired
    private AdminResource adminResource;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        return tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@Valid @RequestBody UserEntity user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        Long pid = new Long(1);
        PlanEntity plan = userService.findPlan(pid);
        user.setPlan(plan);
        user.setStorage(0L);
        userService.register(user, getSiteURL(request));
        return ResponseEntity.ok(user);
    }


    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


    @GetMapping("/verify")
    public void verifyUser(@Param("code") String code, HttpServletResponse response) throws IOException {
        if (userService.verify(code)) {
            response.sendRedirect("http://localhost:3000");
        }
        else {
            response.sendError(0);
        }
    }
}
