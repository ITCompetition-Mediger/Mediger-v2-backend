package net.mediger.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.mediger.auth.service.CookieService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CookieService cookieService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = cookieService.getTokenFromCookie(request);

        if (StringUtils.hasText(token) && isValidateToken(token)) {
            setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    private void setAuthentication(String token) {
        Long id = tokenProvider.getIdFromToken(token);
        String role = tokenProvider.getRoleFromToken(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, null,
                getAuthority(role));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private List<SimpleGrantedAuthority> getAuthority(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
