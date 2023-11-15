package com.oss2.dasom.controller;

import com.oss2.dasom.dto.GetUserResponse;
import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.dto.UpdateUserRequest;
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> userInfo(@PathVariable String userId) {
        GetUserResponse getUserResponse = userService.getUserInfo(userId);
        return ResponseEntity.ok().body(getUserResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) throws IOException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok().body(userService.checkNicknameDuplicate(nickname));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(userId, updateUserRequest);
        return ResponseEntity.ok().body(true);
    }

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
            return ResponseEntity.ok().body(true);
        } else {
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

