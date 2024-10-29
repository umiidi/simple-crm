package az.company.simplecrmtask.mapper;

import az.company.simplecrmtask.models.entities.User;
import az.company.simplecrmtask.models.enums.Role;
import az.company.simplecrmtask.models.request.SignUpRequest;

public enum UserMapper {

    USER_MAPPER;

    public User mapToUser(SignUpRequest request, String encodedPassword, Role defaultRole) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(defaultRole)
                .build();
    }

}
