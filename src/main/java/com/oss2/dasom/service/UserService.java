package com.oss2.dasom.service;

import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.SignUpRequest;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(User user, SignUpRequest signUpRequest) {
        user.setNickname(signUpRequest.getNickname());
        user.setSchool(signUpRequest.getSchool());
        userRepository.save(user);
    }
}
