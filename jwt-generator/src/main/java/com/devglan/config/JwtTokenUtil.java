package com.devglan.config;

import static com.devglan.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.devglan.model.Constants.SIGNING_KEY;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.devglan.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

  public String doGenerateToken(String subject) {

    Claims claims = Jwts.claims().setSubject(subject);
    claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("http://devglan.com")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
        .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        .compact();
  }

  public String generateToken(User user) {
    return doGenerateToken(user.getUsername());
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(SIGNING_KEY)
        .parseClaimsJws(token)
        .getBody();
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername())
        && !isTokenExpired(token);
  }

}