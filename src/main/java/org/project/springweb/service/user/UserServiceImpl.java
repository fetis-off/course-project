package org.project.springweb.service.user;

import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.user.UserRegistrationRequestDto;
import org.project.springweb.dto.user.UserResponseDto;
import org.project.springweb.exception.RegistrationException;
import org.project.springweb.mapper.UserMapper;
import org.project.springweb.model.Role;
import org.project.springweb.model.User;
import org.project.springweb.repository.role.RoleRepository;
import org.project.springweb.repository.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with this email: %s already exists"
                    + requestDto.getEmail());
        }

        User user = userMapper.toUser(requestDto);
        user.setRoles(Set.of(roleRepository.findByRole(Role.RoleName.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userMapper.toUserResponseDto(userRepository.save(user));
    }
}
