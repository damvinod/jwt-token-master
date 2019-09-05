package com.devglan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.devglan.config.JwtTokenUtil;
import com.devglan.model.AuthToken;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
  public ResponseEntity<?> register() throws AuthenticationException {

    /*final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginUser.getUsername(),
            loginUser.getPassword()
            )
        );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final User user = userService.findOne(loginUser.getUsername());*/
    final String token = jwtTokenUtil.doGenerateToken("vinod");
    return ResponseEntity.ok(new AuthToken(token));
  }

}
