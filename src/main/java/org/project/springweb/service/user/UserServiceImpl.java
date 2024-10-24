package org.project.springweb.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.project.springweb.dto.user.UserRegistrationRequestDto;
import org.project.springweb.dto.user.UserResponseDto;
import org.project.springweb.exception.RegistrationException;
import org.project.springweb.mapper.UserMapper;
import org.project.springweb.model.Role;
import org.project.springweb.model.User;
import org.project.springweb.repository.role.RoleRepository;
import org.project.springweb.repository.user.UserRepository;
import org.project.springweb.service.shoppingcart.ShoppingCartService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(String.format("User with this email: %s already exists",
                    requestDto.getEmail())
            );
        }

        User user = userMapper.toUser(requestDto);
        user.setRoles(Set.of(roleRepository.findByRole(Role.RoleName.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        shoppingCartService.createShoppingCart(user);
        return userMapper.toUserResponseDto(user);
    }
}
