package com.example.login_auth_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login_auth_api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String msg);

}
