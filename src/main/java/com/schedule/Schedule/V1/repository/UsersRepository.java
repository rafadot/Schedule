package com.schedule.Schedule.V1.repository;

import com.schedule.Schedule.V1.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users , UUID> {
    Optional<Users> findByEmail(String email);
}
