package ru.covenant.code.landing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.covenant.code.landing.entity.Admins;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class AdminsInitializer implements CommandLineRunner {

    @Value("${admin.default.username}")
    private String defaultUsername;

    @Value("${admin.default.password}")
    private String defaultPassword;

    private final AdminsService adminsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String username = defaultUsername;

        if (adminsService.findByUsername(username).isPresent()) return;

        Admins admin = new Admins();
        admin.setUsername(username);
        admin.setRole("ADMIN");
        admin.setPassword(passwordEncoder.encode(defaultPassword));

        adminsService.save(admin);
    }

}
