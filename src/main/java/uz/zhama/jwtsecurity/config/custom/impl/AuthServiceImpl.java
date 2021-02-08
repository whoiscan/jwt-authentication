package uz.zhama.jwtsecurity.config.custom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zhama.jwtsecurity.config.custom.AuthService;
import uz.zhama.jwtsecurity.entity.Role;
import uz.zhama.jwtsecurity.entity.RoleName;
import uz.zhama.jwtsecurity.entity.User;
import uz.zhama.jwtsecurity.models.AuthResponse;
import uz.zhama.jwtsecurity.models.LoginForm;
import uz.zhama.jwtsecurity.models.SignUpForm;
import uz.zhama.jwtsecurity.repository.RoleRepository;
import uz.zhama.jwtsecurity.repository.UserRepository;
import uz.zhama.jwtsecurity.util.JwtUtil;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtProvider, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginForm loginRequest) {
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

    @Override
    public ResponseEntity<?> signUp(SignUpForm userReq) {
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
