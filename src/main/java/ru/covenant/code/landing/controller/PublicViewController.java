package ru.covenant.code.landing.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.covenant.code.landing.dto.request.AdminRegistrationRqDto;
import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.entity.Admins;
import ru.covenant.code.landing.service.AdminsService;
import ru.covenant.code.landing.service.ClientsService;


/**
 * Контроллер для публичных страниц и форм регистрации.
 */
@Controller
@RequestMapping("/v1")
public class PublicViewController {

    private final ClientsService clientsService;
    private final AdminsService adminsService;

    public PublicViewController(ClientsService clientsService, AdminsService adminsService, PasswordEncoder passwordEncoder) {
        this.clientsService = clientsService;
        this.adminsService = adminsService;
    }


    /**
     * Отображает главную страницу и состояние авторизации.
     *
     * @param model модель представления
     * @return имя шаблона
     */
    @GetMapping({"", "/", "/layout"})
    public String layout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null &&
                auth.isAuthenticated() &&
                !"anonymousUser".equals(auth.getName());

        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            model.addAttribute("username", auth.getName());

            // Проверяем роль ADMIN
            boolean isAdmin = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(authority -> authority.equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "layout";
    }

    /**
     * Страница авторизации.
     *
     * @param model  модель представления
     * @param error  флаг ошибки авторизации
     * @param logout флаг выхода
     * @return имя шаблона
     */
    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return "redirect:/v1/layout";
        }

        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из системы.");
        }

        return "login";
    }


    /**
     * Форма регистрации клиента.
     *
     * @param model   модель представления
     * @param success флаг успешной регистрации
     * @return имя шаблона
     */
    @GetMapping("/registerForm")
    public String registerForm(Model model,
                               @RequestParam(value = "success", required = false) String success) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new ClientsRqDto());
        }

        if (success != null && success.equals("true")) {
            model.addAttribute("showSuccessModal", true);
        }

        return "registerForm";
    }

    /**
     * Обрабатывает отправку формы регистрации клиента.
     *
     * @param clientsRqDto       данные формы
     * @param bindingResult      результат валидации
     * @param redirectAttributes атрибуты редиректа
     * @return адрес редиректа
     */
    @PostMapping("/registerForm")
    public String handleRegisterForm(
            @Valid @ModelAttribute("user") ClientsRqDto clientsRqDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", clientsRqDto);
            return "redirect:/v1/registerForm";
        }

        try {
            clientsService.create(clientsRqDto);
            return "redirect:/v1/registerForm?success=true";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при отправке заявки: " + e.getMessage());
            redirectAttributes.addFlashAttribute("user", clientsRqDto);
            return "redirect:/v1/registerForm";
        }
    }


    /**
     * Форма регистрации администратора.
     *
     * @param model модель представления
     * @return имя шаблона
     */
    @GetMapping("/admin/register")
    public String adminRegisterForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new AdminRegistrationRqDto());
        }
        return "admin-register";
    }


    /**
     * Обрабатывает регистрацию администратора.
     *
     * @param adminRegistrationRqDto данные формы
     * @param bindingResult          результат валидации
     * @param redirectAttributes     атрибуты редиректа
     * @return адрес редиректа
     */
    @PostMapping("/admin/register")
    public String handleAdminRegisterForm(
            @Valid @ModelAttribute("admin") AdminRegistrationRqDto adminRegistrationRqDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        // Проверка совпадения паролей
        if (!adminRegistrationRqDto.getPassword().equals(adminRegistrationRqDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.admin", "Пароли не совпадают");
        }

        // Проверка уникальности имени пользователя
        if (adminsService.findByUsername(adminRegistrationRqDto.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.admin", "Имя пользователя уже занято");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.admin", bindingResult);
            redirectAttributes.addFlashAttribute("admin", adminRegistrationRqDto);
            return "redirect:/v1/admin/register";
        }

        try {
            adminsService.register(adminRegistrationRqDto);

            redirectAttributes.addFlashAttribute("success", "Администратор успешно зарегистрирован!");
            return "redirect:/v1/admin/register";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            redirectAttributes.addFlashAttribute("admin", adminRegistrationRqDto);
            return "redirect:/v1/admin/register";
        }
    }
}