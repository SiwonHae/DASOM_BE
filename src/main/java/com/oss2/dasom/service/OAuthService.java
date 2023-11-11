package com.oss2.dasom.service;

import com.oss2.dasom.auth.oauth.OAuthCodeRequestUrlComposite;
import com.oss2.dasom.auth.oauth.OAuthMemberClientComposite;
import com.oss2.dasom.auth.provider.OAuthServerProvider;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final OAuthCodeRequestUrlComposite oAuthCodeRequestUrlComposite;
    private final OAuthMemberClientComposite oAuthMemberClientComposite;
    private final UserRepository userRepository;

    public String getOAuthCodeRequestUrl(OAuthServerProvider oAuthServerProvider) {
        return oAuthCodeRequestUrlComposite.provide(oAuthServerProvider);
    }

    public String login(OAuthServerProvider oAuthServerProvider, String authCode) {
        User user = oAuthMemberClientComposite.fetch(oAuthServerProvider, authCode);
        User saved = userRepository.findByOauthId(user.getOauthId())
                .orElseGet(() -> userRepository.save(user));
        return saved.getUserId().toString();
    }

    public User getUserById(NanoId userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
