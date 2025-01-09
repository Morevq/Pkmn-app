package su.morevq.pkmn.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import su.morevq.pkmn.dto.UserDto;
import su.morevq.pkmn.models.LoginRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(LoginRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserDto userDetails = UserDto.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .enabled(true)
                .build();

        jdbcUserDetailsManager.createUser(userDetails);
    }
}