package com.vinod.ms.api.jwtclinet.config;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenUtil implements Serializable {

  private Claims getAllClaimsFromToken(String token) {

    return Jwts.parser()
        .setSigningKey(Constants.SIGNING_KEY)
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

  public Boolean validateToken(String token) {
    final String username = getUsernameFromToken(token);
    System.out.println("validateToken....." + username);
    return !isTokenExpired(token);
  }

}
