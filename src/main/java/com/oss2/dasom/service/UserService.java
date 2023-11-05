package com.oss2.dasom.service;

import com.oss2.dasom.config.MyAppProperties;
import com.oss2.dasom.domain.Role;
import com.oss2.dasom.domain.User;
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
    public boolean receiveVerifyMail(User user, SignUpRequest signUpRequest) throws IOException {

        Map<String, Object> cert = verifyUnivEmailCode(signUpRequest);
        Object successObj = cert.get("success");

        if (successObj instanceof Boolean) {
            Boolean success = (Boolean) successObj;
            if (success) {
                user.setNickname(signUpRequest.getNickname());
                user.setSchool(signUpRequest.getSchool());
                user.setRole(Role.ROLE_USER);
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Map<String, Object> verifyUnivEmail(SignUpRequest signUpRequest) throws IOException {
        String key = myAppProperties.getApi_key();
        String email = signUpRequest.getUnivEmail();
        String univName = signUpRequest.getSchool();
        boolean univCheck = true;

        Map<String, Object> cert = UnivCert.certify(key, email, univName, univCheck);
        return cert;
    }

    public Map<String, Object> verifyUnivEmailCode(SignUpRequest signUpRequest) throws IOException {
        String key = myAppProperties.getApi_key();
        String email = signUpRequest.getUnivEmail();
        String univName = signUpRequest.getSchool();
        int verifyCode = signUpRequest.getInputVerifyCode();

        Map<String, Object> cert = UnivCert.certifyCode(key, email, univName, verifyCode);
        return cert;
    }
}
