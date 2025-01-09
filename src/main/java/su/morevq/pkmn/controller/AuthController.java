package su.morevq.pkmn.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import su.morevq.pkmn.models.LoginRequest;
import su.morevq.pkmn.security.jwt.JwtService;
import su.morevq.pkmn.security.service.LoginService;
import su.morevq.pkmn.security.service.RegistrationService;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final LoginService loginService;

    private final RegistrationService registrationService;

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    @PostMapping("/register")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws CredentialException {
        if (!jdbcUserDetailsManager.userExists(loginRequest.getUsername())) {
            registrationService.registerUser(loginRequest);
        }
        String jwt = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }


    @PostMapping("/success")
    public ResponseEntity<String> success(@AuthenticationPrincipal UserDetails user,
                                          HttpServletResponse response) throws IOException {

        String jwt = jwtService.createJwt(user.getUsername(), user.getAuthorities().iterator().next());

        response.addCookie(
                new Cookie("jwt", Base64.getEncoder().encodeToString(jwt.getBytes(StandardCharsets.UTF_8)))
        );

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        ClassPathResource resource = new ClassPathResource("success.html");
        String success = new String(Files.readAllBytes(resource.getFile().toPath()));

        return ResponseEntity.ok()
                .body(success);
    }
}