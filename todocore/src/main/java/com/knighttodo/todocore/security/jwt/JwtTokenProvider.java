package com.knighttodo.todocore.security.jwt;

import com.knighttodo.todocore.domain.RoleVO;
import com.knighttodo.todocore.domain.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

//    @Value("${jwt.token.secret}")
    private String secret = "somesecret";

//    @Value("${jwt.token.expired}")
    private long validityInMilliseconds = 3600000;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserVO userVO) {

        Claims claims = Jwts.claims().setSubject(userVO.getLogin());
        claims.put("roles", getRoleNames(userVO.getRoles()));

        Date currentTimeStamp = new Date();
        Date expirationTimeStamp = new Date(currentTimeStamp.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(currentTimeStamp)
            .setExpiration(expirationTimeStamp)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("jwt token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<RoleVO> roles) {
        return roles.stream().map(RoleVO::getName).collect(Collectors.toList());
    }
}
