package uz.zhama.jwtsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin() {
        return "Hi Admin";
    }

    @GetMapping("/userpage")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUser() {
        return "Hi User";
    }
}
