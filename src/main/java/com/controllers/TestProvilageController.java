package com.controllers;

import com.services.ProfileSevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProvilageController {

    @Autowired
    private ProfileSevices profileSevices;

    @GetMapping("all")
    public String all() {
        return profileSevices.all();
    }

    @GetMapping("edit")
    public String edit() {
        return profileSevices.edit();
    }

    @GetMapping("read")
    public String read() {
        return profileSevices.read();
    }

    @GetMapping("delete")
    public String delete() {
        return profileSevices.delete();
    }
}
