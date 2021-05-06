package net.ion.sst.api_sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @GetMapping("/{userId}")
    public String getAdminById(@PathVariable("userId") String userId)
    {
        return "Hello " + userId;
    }
}
