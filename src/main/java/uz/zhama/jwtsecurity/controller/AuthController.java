package uz.zhama.jwtsecurity.controller;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.config.custom.AuthService;
import uz.zhama.jwtsecurity.models.LoginForm;
import uz.zhama.jwtsecurity.models.SignUpForm;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //for getting tokens of registered users
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    //for signing only users to db
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm userReq) {
        return authService.signUp(userReq);
    }

}
