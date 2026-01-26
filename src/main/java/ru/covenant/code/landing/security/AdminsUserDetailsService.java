package ru.covenant.code.landing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.covenant.code.landing.entity.Admins;
import ru.covenant.code.landing.service.AdminsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminsUserDetailsService implements UserDetailsService {

    private final AdminsService adminsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admins admin = adminsService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Администратор с таким именем не найден: " + username));

        return new User(
                admin.getUsername(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + admin.getRole()))
        );
    }
}
