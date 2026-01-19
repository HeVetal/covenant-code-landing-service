package ru.covenant.code.landing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.covenant.code.landing.entity.Clients;

import java.util.UUID;

public interface ClientsRepository extends JpaRepository<Clients, UUID> {
}
