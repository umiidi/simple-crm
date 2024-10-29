package az.company.simplecrmtask.security.service;

import az.company.simplecrmtask.security.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static az.company.simplecrmtask.security.filter.AuthFilter.AUTHORITIES_CLAIM;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecurityProperties securityProperties;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getJwtProperties().getSecretKey());
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String create(Authentication authentication) {
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(securityProperties.getJwtProperties().getAccessTokenValidityInSeconds())))
                .signWith(key, SignatureAlgorithm.HS256);
        jwtBuilder.addClaims(Map.of(
                AUTHORITIES_CLAIM,
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList()));
        return jwtBuilder.compact();
    }

    public Claims read(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
