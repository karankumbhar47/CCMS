package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/v1/complain")
public class complainController {
    @GetMapping
    public String allComplain(){
        return "all complain";
    }
}


