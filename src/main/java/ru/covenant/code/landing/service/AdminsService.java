package ru.covenant.code.landing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.covenant.code.landing.dto.request.AdminRegistrationRqDto;
import ru.covenant.code.landing.entity.Admins;
import ru.covenant.code.landing.repository.AdminsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminsService {

    private final AdminsRepository adminsRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Admins> findByUsername(String username) {
        return adminsRepository.findByUsername(username);
    }

    public Admins save(Admins admin) {
        return adminsRepository.save(admin);
    }

    public Admins register(AdminRegistrationRqDto dto) {
        if (isUsernameTaken(dto.getUsername())) {
            throw new IllegalArgumentException("Имя пользователя уже занято");
        }

        Admins admin = new Admins();
        admin.setUsername(dto.getUsername());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(dto.getRole());

        return adminsRepository.save(admin);
    }

    public boolean isUsernameTaken(String username) {
        return adminsRepository.findByUsername(username).isPresent();
    }
}
