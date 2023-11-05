package com.oss2.dasom.auth;

import com.oss2.dasom.auth.provider.KakaoUserInfo;
import com.oss2.dasom.auth.provider.NaverUserInfo;
import com.oss2.dasom.auth.provider.OAuth2UserInfo;
import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Role;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }
        else if(provider.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;

        String email = oAuth2UserInfo.getEmail();
        String realname = oAuth2UserInfo.getRealname();
        int age = oAuth2UserInfo.getAge();
        Gender gender = oAuth2UserInfo.getGender();
        Role role = Role.ROLE_NOTUSER;

        String nickname = "따로입력";
        String school = "따로입력";
        String univEmail = "따로입력";

        User byUsername = userRepository.findByEmail(email);

        //DB에 없는 사용자라면 회원가입
        if(byUsername == null){
            byUsername = User.oauth2Register()
                    .username(username).email(email).role(role).realname(realname)
                    .age(age).nickname(nickname).school(school).gender(gender).univEmail(univEmail)
                    .provider(provider).providerId(providerId)
                    .build();

            byUsername.setUserId(NanoId.makeId());
            userRepository.save(byUsername);

            System.out.println(byUsername);
        }

        return new PrincipalDetails(byUsername, oAuth2UserInfo);
    }
}