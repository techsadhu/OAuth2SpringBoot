package com.example.zuuldemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @PostMapping("/getauthtoken")
    public String getAuthToken(){
        return "token returned";
    }
}
