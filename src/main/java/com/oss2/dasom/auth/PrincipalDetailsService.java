package com.oss2.dasom.auth;

import com.oss2.dasom.domain.User;
import com.oss2.dasom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User byUsername = userRepository.findByEmail(email);
        if (byUsername != null) {
            return new PrincipalDetails(byUsername);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}