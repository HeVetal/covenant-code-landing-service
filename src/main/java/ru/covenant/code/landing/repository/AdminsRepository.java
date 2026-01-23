package ru.covenant.code.landing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.covenant.code.landing.entity.Admins;

import java.util.Optional;

public interface AdminsRepository extends JpaRepository<Admins, Long> {
    Optional<Admins> findByUsername(String username);
}
