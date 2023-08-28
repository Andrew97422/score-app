package com.bigdata.user.service;

import com.bigdata.user.model.dto.UserInfo;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new EntityNotFoundException("Пользователь не был найден");
        });
        var response = new UserResponse();
        response.mapEntityToDto(user);

        return response;
    }

    public int createNewUser(UserInfo userInfo) {
        var user = userInfo.mapDtoToEntity();
        try {
            userRepository.save(user);
            log.info("User {} saved.", user.getId());
            return user.getId();
        } catch (Exception e) {
            log.error("User {} wasn't saved. Error: {}", user.getId(), e.getMessage());
            throw e;
        }
    }

    public Integer updateUserById(int id, UserInfo userInfo) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new EntityNotFoundException("Пользователь не был найден");
        });

        var updatedUser = userInfo.mapDtoToEntity();

        user.setBirthday(updatedUser.getBirthday());
        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setPhone(updatedUser.getPhone());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setSurName(updatedUser.getSurName());
        user.setObtainedFrom(updatedUser.isObtainedFrom());

        userRepository.saveAndFlush(user);

        log.info("User {} was successfully updated.", id);
        return id;
    }

    public void deleteUserById(int id) {
        try {
            userRepository.deleteById(id);
            log.info("User {} was deleted", id);
        } catch (Exception e) {
            log.error("User {} wasn't deleted. Reason: ", e.getMessage());
        }
    }
}
