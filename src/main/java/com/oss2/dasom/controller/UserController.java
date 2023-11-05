package com.oss2.dasom.controller;

import com.oss2.dasom.auth.PrincipalDetails;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.repository.UserRepository;
import com.oss2.dasom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users/info")
    public ResponseEntity<?> userInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        return ResponseEntity.ok().body(user.toString());
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> createUser(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) throws IOException {
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByEmail(email);

        boolean sucessSendMail = userService.sendVerifyMail(signUpRequest);
        if (sucessSendMail) {
            boolean sucessReceiveMail = userService.receiveVerifyMail(user, signUpRequest);
            if (sucessReceiveMail) {
                return ResponseEntity.ok().body("회원가입 성공");
            } else {
                return ResponseEntity.ok().body("실패 인증번호 틀림");
            }
        } else {
            return ResponseEntity.ok().body("실패 인증메일 보내기");
        }
    }
}

