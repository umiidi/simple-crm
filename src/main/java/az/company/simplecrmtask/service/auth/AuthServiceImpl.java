package az.company.simplecrmtask.service.auth;

import az.company.simplecrmtask.exception.type.EmailAlreadyExistsException;
import az.company.simplecrmtask.models.entities.User;
import az.company.simplecrmtask.models.enums.Role;
import az.company.simplecrmtask.models.request.SignInRequest;
import az.company.simplecrmtask.models.request.SignUpRequest;
import az.company.simplecrmtask.models.response.SignInResponse;
import az.company.simplecrmtask.repository.UserRepository;
import az.company.simplecrmtask.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static az.company.simplecrmtask.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Role DEFAULT_ROLE_USER = Role.USER;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword());

        authentication = authenticationManager.authenticate(authentication);

        return SignInResponse.builder()
                .accessToken(jwtService.create(authentication))
                .build();
    }

    @Override
    public void signUp(SignUpRequest request) {
        checkPasswordsEquality(request);

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(String.format("the email %s is already registered", request.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = USER_MAPPER.mapToUser(request, encodedPassword, DEFAULT_ROLE_USER);
        userRepository.save(user);
    }

    private void checkPasswordsEquality(SignUpRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("passwords do not match");
        }
    }

}
