package com.bigdata.user.repository;

import com.bigdata.user.model.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @NotNull Optional<UserEntity> findById(@NotNull Integer id);
    Optional<UserEntity> findByLogin(String login);
    boolean existsByLogin(String login);
}
