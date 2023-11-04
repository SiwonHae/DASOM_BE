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

    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }


    @GetMapping("/form/loginInfo")
    @ResponseBody
    public String formLoginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        return user.toString();
    }


    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);
        return attributes.toString();     //세션에 담긴 user가져올 수 있음음
    }

    @PostMapping("/oauth/test")
    @ResponseBody
    public String testPost(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println(attributes);

        return attributes.toString();     //세션에 담긴 user가져올 수 있음음
    }

    @GetMapping("/oauth/myinfo")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        model.addAttribute("oauth2User", oauth2User);
        return "userinfo";
    }

    @PostMapping("/saveUserInfo")
    @ResponseBody
    public String saveUserInfo(@AuthenticationPrincipal OAuth2User oauth2User, SignUpRequest signUpRequest) {
        String email = oauth2User.getAttribute("email");

        User user = userRepository.findByEmail(email);

        userService.createUser(user, signUpRequest);

        return user.toString();
    }


    @GetMapping("/loginInfo")
    @ResponseBody
    public String loginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String result = "";

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        if(principal.getUser().getProvider() == null) {
            result = result + "Form 로그인 : " + principal;
        }else{
            result = result + "OAuth2 로그인 : " + principal;
        }
        return result;
    }
}