package az.company.simplecrmtask.security.filter;

import az.company.simplecrmtask.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    public static final String AUTHORITIES_CLAIM = "authorities";
    public static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<Authentication> authentication = authenticate(request);
        authentication.ifPresent((auth -> SecurityContextHolder.getContext().setAuthentication(auth)));
        filterChain.doFilter(request, response);
    }

    private Optional<Authentication> authenticate(HttpServletRequest request) {
        final Optional<String> token = getToken(request);
        if (token.isEmpty()) {
            return Optional.empty();
        }

        final Claims claims;

        try {
            claims = jwtService.read(token.get());
        } catch (JwtException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("unexpected error", e);
        }

        List<?> authorities = claims.get(AUTHORITIES_CLAIM, List.class);
        Collection<? extends GrantedAuthority> userAuthorities = authorities
                .stream()
                .map(a -> new SimpleGrantedAuthority(a.toString()))
                .toList();

        UserDetails user = userDetailsService.loadUserByUsername(claims.getSubject());

        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, "", userAuthorities);
        return Optional.of(authenticationToken);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
            return Optional.of(headerAuth.substring(7));
        }
        return Optional.empty();
    }

}