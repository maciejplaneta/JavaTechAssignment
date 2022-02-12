package com.example.javatechassignment.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class SimpleUploadFormController {

    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }
}
