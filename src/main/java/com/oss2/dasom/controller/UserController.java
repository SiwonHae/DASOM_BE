package com.oss2.dasom.controller;

import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.createUser(signUpRequest);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/sendmail")
    public ResponseEntity<?> sendMail(@RequestBody SignUpRequest signUpRequest) throws IOException {

        System.out.println(signUpRequest);
        boolean successSendMail = userService.sendVerifyMail(signUpRequest);

        if (successSendMail) {
            System.out.printf("성공" + successSendMail);
            return ResponseEntity.ok().body(true);
        } else {
            System.out.printf("실패" + successSendMail);
            return ResponseEntity.ok().body(false);
        }
    }

    @PostMapping("/verifymail")
    public ResponseEntity<?> verifyEmail(@RequestBody SignUpRequest signUpRequest) throws IOException {

        boolean successReceiveMail = userService.receiveVerifyMail(signUpRequest);

        if (successReceiveMail) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.ok().body(false);
        }
    }

}

