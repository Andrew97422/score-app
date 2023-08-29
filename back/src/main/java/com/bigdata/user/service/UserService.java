package com.bigdata.user.service;

import com.bigdata.user.model.dto.UserInfo;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElseThrow(() -> {
            log.error("There's no user with username {}", username);
            return new UsernameNotFoundException("User " + username + " wasn't found");
        });
    }

    /*
    public void login(Login loginRequest) throws BadCredentialsException {

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getLogin(), loginRequest.getPassword()
            );
            UserDetails user = loadUserByUsername(loginRequest.getLogin());
            if (user.getPassword().equals(loginRequest.getPassword())) {

            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }*/
}