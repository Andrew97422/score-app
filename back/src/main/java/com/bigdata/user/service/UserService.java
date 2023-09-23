package com.bigdata.user.service;

import com.bigdata.auth.model.RegisterRequest;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new UsernameNotFoundException("Пользователь не был найден");
        });
        var response = new UserResponse();
        response.mapEntityToDto(user);

        return response;
    }

    @Transactional
    public Integer updateUserById(int id, RegisterRequest registerRequest) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new UsernameNotFoundException("Пользователь не был найден");
        });

        var updatedUser = registerRequest.mapDtoToEntity(false);

        user.updateData(updatedUser);
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        userRepository.saveAndFlush(user);

        log.info("User {} was successfully updated.", id);
        return id;
    }

    @Transactional
    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
            log.info("User {} was deleted", id);
        } catch (Exception e) {
            log.error("User {} wasn't deleted. Reason: ", id, e);
        }
    }
}
