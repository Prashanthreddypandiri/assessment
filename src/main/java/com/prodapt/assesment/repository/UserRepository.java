package com.prodapt.assesment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.prodapt.assesment.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}