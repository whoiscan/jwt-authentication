package uz.zhama.jwtsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.zhama.jwtsecurity.entity.Role;
import uz.zhama.jwtsecurity.entity.RoleName;
import uz.zhama.jwtsecurity.entity.User;
import uz.zhama.jwtsecurity.models.LoginForm;
import uz.zhama.jwtsecurity.models.AuthResponse;
import uz.zhama.jwtsecurity.models.SignUpForm;
import uz.zhama.jwtsecurity.repository.RoleRepository;
import uz.zhama.jwtsecurity.repository.UserRepository;
import uz.zhama.jwtsecurity.util.JwtUtil;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;


    //for getting tokens of registered users
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    //for signing only users to db
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpForm userReq) {
        if (userRepository.existsByUsername(userReq.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User(userReq.getName(), userReq.getUsername(), userReq.getPassword());

        user.setPassword(encoder.encode(user.getPassword()));
        //setting register user role USER only not admin!!!
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        user.setRoles(Collections.singletonList(userRole));


        userRepository.save(user);
        return ResponseEntity.ok().body("User signed up successfully");
        /*
        Alternative way to show Response entity created...
        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body("User registered successfully!");*/

    }

}
