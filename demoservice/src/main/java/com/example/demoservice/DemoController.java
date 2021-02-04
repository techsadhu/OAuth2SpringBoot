package com.example.demoservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/helloworld")
    private String helloWorld(){
        logger.debug("====In Demo Controller Hello World====");
        return "Hi Rahul How are you!!";
    }

    @PostMapping("/getauthtoken")
    public String getAuthToken(){
        logger.debug("====In Demo Controller getauthtoken====");
        return "token returned";
    }
}
