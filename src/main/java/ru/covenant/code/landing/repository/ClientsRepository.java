package ru.covenant.code.landing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.covenant.code.landing.entity.Clients;

import java.util.UUID;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, UUID> {
}
