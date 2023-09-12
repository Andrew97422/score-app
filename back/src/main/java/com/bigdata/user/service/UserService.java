package com.bigdata.user.service;

import com.bigdata.user.model.dto.LoginRequest;
import com.bigdata.user.model.dto.UserInfo;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse getUserById(int id) {
        var user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User {} wasn't found", id);
            return new UsernameNotFoundException("Пользователь не был найден");
        });
        var response = new UserResponse();
        response.mapEntityToDto(user);

        return response;
    }

    public int createNewUser(UserInfo userInfo) {
        var user = userInfo.mapDtoToEntity(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        var updatedUser = userInfo.mapDtoToEntity(false);

        user.setBirthday(updatedUser.getBirthday());
        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
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

    public Integer login(LoginRequest loginRequest) {
        String login = loginRequest.getLogin();
        UserDetails userDetails = loadUserByUsername(login);

        log.info("Found user with credentials: login - {}", userDetails.getUsername());

        if (Objects.equals(login, userDetails.getUsername()) &&
                passwordEncoder.matches(loginRequest.getPassword(),userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getLogin(), loginRequest.getPassword()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return userRepository.findByLogin(login).
                orElseThrow(() -> new UsernameNotFoundException("User not found!")).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElseThrow(() -> {
            log.error("There's no user with username {}", username);
            return new UsernameNotFoundException("User " + username + " wasn't found");
        });
    }
}
