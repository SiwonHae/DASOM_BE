//package com.oss2.dasom.controller;
//
//import com.oss2.dasom.domain.User;
//import com.oss2.dasom.dto.SignUpRequest;
//import com.oss2.dasom.repository.UserRepository;
//import com.oss2.dasom.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserRepository userRepository;
//    private final UserService userService;
//
//    @GetMapping("/users/info")
//    public ResponseEntity<?> userInfo(Authentication authentication){
//
//        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//        User user = principal.getUser();
//
//        return ResponseEntity.ok().body(user.toString());
//    }
//
////    @PostMapping("/users/sendmail")
////    public ResponseEntity<?> sendMail(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) throws IOException {
////        String email = oauth2User.getAttribute("email");
////        User user = userRepository.findByEmail(email);
////
////        boolean successSendMail = userService.sendVerifyMail(signUpRequest);
////
////        if (successSendMail) {
////            return ResponseEntity.ok().body(true);
////        } else {
////            return ResponseEntity.ok().body(false);
////        }
////    }
//
////    @PostMapping("/users/verifymail")
////    public ResponseEntity<?> verifyEmail(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) throws IOException {
////        String email = oauth2User.getAttribute("email");
////        User user = userRepository.findByEmail(email);
////
////        boolean successReceiveMail = userService.receiveVerifyMail(user, signUpRequest);
////
////        if (successReceiveMail) {
////            return ResponseEntity.ok().body(true);
////        } else {
////            return ResponseEntity.ok().body(false);
////        }
////    }
////
////    @PostMapping("/users/signup")
////    public ResponseEntity<?> signup(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) {
////        String email = oauth2User.getAttribute("email");
////        User user = userRepository.findByEmail(email);
////
////        userService.createUser(user, signUpRequest);
////
////        return ResponseEntity.ok().body(true);
////    }
//}
//
