package com.oss2.dasom.User;

import com.oss2.dasom.NanoId;
import com.oss2.dasom.User.User;
import com.oss2.dasom.User.dto.SignUpRequest;
import com.oss2.dasom.User.UserRepository;
import com.oss2.dasom.User.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createUser(SignUpRequest signUpRequest) {
        userRepository.save(User.builder()
                .userId(NanoId.makeId())
                .role(Role.ROLE_USER)
                .email(signUpRequest.email())
                .school(signUpRequest.school())
                .gender(signUpRequest.gender())
                .nickname(signUpRequest.nickname())
                .password(bCryptPasswordEncoder.encode(signUpRequest.password()))
                .build());
    }

    @Transactional
    public void updateUser(UpdateUserRequest updateUserRequest, NanoId userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        user.setNickname(updateUserRequest.nickname());
        userRepository.save(user);
    }
}
