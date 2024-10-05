package com.bumsoap.petcare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerGreet {
    @GetMapping("/greet")
    public ResponseEntity<String> greetWorld() {
        String message = "안녕하세요, 온세상 여러분!";
        return ResponseEntity.ok(message);
    }
}
