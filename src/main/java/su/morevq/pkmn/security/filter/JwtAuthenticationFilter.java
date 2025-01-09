package su.morevq.pkmn.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import su.morevq.pkmn.security.jwt.JwtService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info(String.valueOf(request.getHeaderNames()));

        String jwtToken = request.getHeader("Authorization");

        log.info("Get jwt token from header {}", jwtToken);

        if ((Objects.isNull(jwtToken)) || !jwtToken.startsWith("Bearer ")) {
            if (!Objects.isNull(request.getCookies())) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("jwt")) {
                        jwtToken = new String(Base64.getDecoder().decode(cookie.getValue()));
                        log.info("Find jwt token from cookie");
                    }
                    System.out.println(jwtToken);
                }
            }
        }
        if (!Objects.isNull(jwtToken) && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }

        if (!Objects.isNull(jwtToken)) {
            log.info("Jwt token did not find");
            DecodedJWT decodedJWT = jwtService.verify(jwtToken);
            if(!Objects.isNull(decodedJWT)){
                String username = decodedJWT.getSubject();
                String authority = decodedJWT.getClaim("authority").asString();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, List.of(simpleGrantedAuthority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        log.info("Get jwt token {}", jwtToken);

        filterChain.doFilter(request, response);

    }

}
