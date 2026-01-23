package ru.covenant.code.landing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.covenant.code.landing.entity.Admins;
import ru.covenant.code.landing.repository.AdminsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminsService {

    private final AdminsRepository adminsRepository;

    public Optional<Admins> findByUsername(String username) {
        return adminsRepository.findByUsername(username);
    }

    public Admins save(Admins admin) {
        return adminsRepository.save(admin);
    }
}
