package com.vinod.ms.api.jwtclinet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JwtTokenNotFoundException extends RuntimeException {

  public JwtTokenNotFoundException(String message) {
    super(message);
  }
}
