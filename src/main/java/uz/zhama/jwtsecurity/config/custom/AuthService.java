package uz.zhama.jwtsecurity.config.custom;

import org.springframework.http.ResponseEntity;
import uz.zhama.jwtsecurity.models.LoginForm;
import uz.zhama.jwtsecurity.models.SignUpForm;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginForm loginRequest);

    ResponseEntity<?> signUp(SignUpForm userReq);
}
