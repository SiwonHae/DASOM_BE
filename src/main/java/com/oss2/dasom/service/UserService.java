package com.oss2.dasom.service;

import com.oss2.dasom.config.MyAppProperties;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Role;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.GetUserResponse;
import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.repository.UserRepository;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final MyAppProperties myAppProperties;
    private final UserRepository userRepository;

    public GetUserResponse getUserInfo(String userId) {
        User user = userRepository.findByUserId(NanoId.of(userId)).orElseThrow(() ->
                new IllegalArgumentException("올바르지 않은 회원입니다."));
        return GetUserResponse.builder()
                .nickname(user.getNickname())
                .school(user.getSchool())
                .build();
    }

    public boolean sendVerifyMail(SignUpRequest signUpRequest) throws IOException {

        Map<String, Object> cert = verifyUnivEmail(signUpRequest);
        Object successObj = cert.get("success");

        if (successObj instanceof Boolean) {
            Boolean success = (Boolean) successObj;
            if (success) {
                // 인증 메일 보내기 성공
                return true;
            } else {
                // 인증 메일 보내기 실패
                return false;
            }
        }
        return false;
    }

    @Transactional
    public boolean receiveVerifyMail(SignUpRequest signUpRequest) throws IOException {

        Map<String, Object> cert = verifyUnivEmailCode(signUpRequest);
        Object successObj = cert.get("success");

        if (successObj instanceof Boolean) {
            Boolean success = (Boolean) successObj;
            if (success) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Map<String, Object> verifyUnivEmail(SignUpRequest signUpRequest) throws IOException {
        String key = myAppProperties.getApi_key();
        String email = signUpRequest.getUnivemail();
        String univName = signUpRequest.getSchool();
        boolean univCheck = true;

        Map<String, Object> cert = UnivCert.certify(key, email, univName, univCheck);
        return cert;
    }

    public Map<String, Object> verifyUnivEmailCode(SignUpRequest signUpRequest) throws IOException {
        String key = myAppProperties.getApi_key();
        String email = signUpRequest.getUnivemail();
        String univName = signUpRequest.getSchool();
        int verifyCode = signUpRequest.getInputVerifyCode();

        Map<String, Object> cert = UnivCert.certifyCode(key, email, univName, verifyCode);
        return cert;
    }

    public void createUser(SignUpRequest signUpRequest) {

        User user = userRepository.findByUserId(NanoId.of(signUpRequest.getUserId())).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        user.setNickname(signUpRequest.getNickname());
        user.setUnivEmail(signUpRequest.getUnivemail());
        user.setSchool(signUpRequest.getSchool());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public void deleteUser(String userId) throws IOException {
        User user = userRepository.findByUserId(NanoId.of(userId)).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        String key = myAppProperties.getApi_key();
        UnivCert.clear(key, user.getUnivEmail());

        userRepository.deleteById(NanoId.of(userId));
    }
}
