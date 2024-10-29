package az.company.simplecrmtask.service.auth;

import az.company.simplecrmtask.models.request.SignInRequest;
import az.company.simplecrmtask.models.request.SignUpRequest;
import az.company.simplecrmtask.models.response.SignInResponse;

public interface AuthService {

    SignInResponse signIn(SignInRequest request);

    void signUp(SignUpRequest request);

}
