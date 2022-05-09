package com.cloudforstorage.homecloudserver.repository;

import com.cloudforstorage.homecloudserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String userName);
    UserEntity save(UserEntity userEntity);
}
