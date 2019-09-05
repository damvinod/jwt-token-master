package com.vinod.ms.api.jwtclinet.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import com.vinod.ms.api.jwtclinet.exception.JwtInvalidTokenException;
import com.vinod.ms.api.jwtclinet.exception.JwtTokenNotFoundException;

public class JwtAuthenticationFilter extends GenericFilterBean {

  private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String header = httpServletRequest.getHeader(Constants.HEADER_STRING);
    String authToken = null;

    if (header == null || ! header.startsWith(Constants.TOKEN_PREFIX)) {
      throw new JwtTokenNotFoundException("token is " + header);
    }

    authToken = header.replace(Constants.TOKEN_PREFIX, "");

    try {
      String userName = jwtTokenUtil.getUsernameFromToken(authToken);

      Authentication authentication = userName != null ? new UsernamePasswordAuthenticationToken(userName, null,java.util.Collections.emptyList()): null;

      SecurityContextHolder.getContext().setAuthentication(authentication);

      System.out.println("userName...." + userName);

    } catch (Exception e) {
      throw new JwtInvalidTokenException("token is " + header);
    }

    if(! jwtTokenUtil.validateToken(authToken)) {
      throw new JwtInvalidTokenException("token is " + header);
    }

    filterChain.doFilter(request, response);
  }
}