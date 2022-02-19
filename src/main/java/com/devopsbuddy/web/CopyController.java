package com.devopsbuddy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CopyController {
    
    //@CrossOrigin
    @RequestMapping("/about")
    public String about() {
        return "copy/about";
    }
}
