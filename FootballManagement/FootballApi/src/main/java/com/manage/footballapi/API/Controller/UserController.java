package com.manage.footballapi.API.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/hello")
@RestController
public class UserController {

    @GetMapping("/all")
    public String hello (){
        return "Hello Youtube";
    }

    @PreAuthorize("hasAnyRole(' ADMIN')")
    @GetMapping("/secured/all")
    public String helloSecured(){
        return "Hello more secured youtube";
    }
}
