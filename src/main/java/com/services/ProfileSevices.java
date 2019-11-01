package com.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ProfileSevices {
    @PreAuthorize("hasAuthority('EDIT_PROFILE') and hasAuthority('DELETE_PROFILE') and hasAuthority('READ_PROFILE')")
    public String all() {
        return "EDIT_PROFILE READ_PROFILE DELETE_PROFILE";
    }

    @PreAuthorize("hasAuthority('EDIT_PROFILE')")
    public String edit() {
        return "EDIT_PROFILE";
    }

    @PreAuthorize("hasAuthority('READ_PROFILE')")
    public String read() {
        return "READ_PROFILE";
    }

    @PreAuthorize("hasAuthority('DELETE_PROFILE')")
    public String delete() {
        return "DELETE_PROFILE";
    }
}
