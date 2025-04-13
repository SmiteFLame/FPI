package com.point.fpi.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping
    public String init() {
        return "잘부탁드립니다.";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
