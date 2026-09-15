package com.devguardian.user;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserProfileDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserProfileDTO(user.getId(), user.getEmail(), user.getRole());
    }

    public UserProfileDTO updateUser(Long id, UpdateUserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(dto.getEmail());
        userRepository.save(user);
        return new UserProfileDTO(user.getId(), user.getEmail(), user.getRole());
    }

    public void deleteUser(Long id) { userRepository.deleteById(id); }

    public List<User> listAllUsers() { return userRepository.findAll(); }
}