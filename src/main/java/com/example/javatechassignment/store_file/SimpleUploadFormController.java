package com.example.javatechassignment.store_file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class SimpleUploadFormController {

    @GetMapping("/uploadForm")
    public String uploadForm() {
        return "uploadForm";
    }
}
