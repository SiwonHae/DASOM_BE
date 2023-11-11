package com.oss2.dasom.controller;

import com.oss2.dasom.auth.provider.OAuthServerProvider;
import com.oss2.dasom.service.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @SneakyThrows
    @GetMapping("/{oAuthServerProvider}")
    public ResponseEntity<?> redirectOAuthCodeRequestUrl(
            @PathVariable OAuthServerProvider oAuthServerProvider,
            HttpServletResponse response
            ) {
        String redirectUrl = oAuthService.getOAuthCodeRequestUrl(oAuthServerProvider);
        response.sendRedirect(redirectUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oAuthServerProvider}")
    public ResponseEntity<?> login(
            @PathVariable OAuthServerProvider oAuthServerProvider,
            @RequestParam("code") String code
    ) {
        String login = oAuthService.login(oAuthServerProvider, code);
        return ResponseEntity.ok(login);
    }
}
