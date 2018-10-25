package com.example.SpringBoot.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
public class Greeting {
    @GetMapping("/greeting")
    public String greet() {
        return "Hello World!";
    }
}
