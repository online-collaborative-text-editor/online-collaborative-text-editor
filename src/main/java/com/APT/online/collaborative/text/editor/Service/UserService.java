package com.APT.online.collaborative.text.editor.Service;

import com.APT.online.collaborative.text.editor.Model.UserEntity;
import com.APT.online.collaborative.text.editor.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserEntity user) {
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, UserEntity user) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist"));

        if (user.getUsername() != null && !user.getUsername().isEmpty() && !user.getUsername().equals(existingUser.getUsername())) {
            if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
                throw new IllegalStateException("Username already taken");
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty() && !user.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(user.getPassword());
        }
    }
}
