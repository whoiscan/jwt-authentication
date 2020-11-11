package uz.zhama.jwtsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {

    @GetMapping(value = {"/auth/cabinet"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin() {
        return "Admin page";
    }

    @GetMapping(value = {"/auth/user"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public String getUser() {
        return "User page";
    }

}
