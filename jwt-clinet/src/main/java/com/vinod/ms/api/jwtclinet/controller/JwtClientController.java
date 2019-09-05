package com.vinod.ms.api.jwtclinet.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtClientController {

  @PostMapping(path="/getUsers")
  public String getUsers() {
    return "Authenticated";
  }

  @PostMapping(path="/vinod")
  public String vinod() {
    return "vinod";
  }
}
