package com.APT.online.collaborative.text.editor.Repository;

import com.APT.online.collaborative.text.editor.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findUserByUsername(String username);
    Boolean existsByUsername(String username);
}
