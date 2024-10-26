package com.DLBCSPSE01.referentx.repository;

import com.DLBCSPSE01.referentx.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
}