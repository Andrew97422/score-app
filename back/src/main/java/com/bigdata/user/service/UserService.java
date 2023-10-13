package com.bigdata.user.service;

import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public UserResponse getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new UsernameNotFoundException("Пользователь не был найден");
        });

        return userUtils.mapToDto(user);
    }

    @Transactional
    public String updateUserById(int id, Map<String, String> map) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new UsernameNotFoundException("Пользователь не был найден");
        });

        userUtils.updateData(user, map);
        userRepository.saveAndFlush(user);

        StringBuilder builder = new StringBuilder();
        builder.append(user.getLastName());
        builder.append(" ");
        builder.append(user.getFirstName());
        builder.append(" ");
        builder.append(user.getSurName());
        builder.append(" ").append("(").append(user.getLogin()).append(")");
        log.info("User {} was successfully updated.", builder);
        return builder.toString();
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
