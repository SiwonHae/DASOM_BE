package com.oss2.dasom.controller;

import com.oss2.dasom.auth.PrincipalDetails;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.repository.UserRepository;
import com.oss2.dasom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }


    @GetMapping("/users/info")
    @ResponseBody
    public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        return user.toString();
    }

    @GetMapping("/users/signup")
    public String createGet(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        model.addAttribute("oauth2User", oauth2User);
        return "userinfo";
    }

    @PostMapping("/users/signup")
    @ResponseBody
    public String createUser(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) throws IOException {
        String email = oauth2User.getAttribute("email");
        User user = userRepository.findByEmail(email);

        boolean sucessSendMail = userService.sendVerifyMail(signUpRequest);
        if (sucessSendMail) {
            boolean sucessReceiveMail = userService.receiveVerifyMail(user, signUpRequest);
            if (sucessReceiveMail) {
                return "회원가입 성공";
            } else {
                return "회원가입 실패(인증 번호 틀림)";
            }
        } else {
            return "인증 메일 보내기 실패";
        }
    }
}

