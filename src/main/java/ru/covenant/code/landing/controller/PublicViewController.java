package ru.covenant.code.landing.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.covenant.code.landing.dto.request.ClientsRqDto;
import ru.covenant.code.landing.service.ClientsService;

@Controller
@RequestMapping("/v1")
public class PublicViewController {

    private final ClientsService clientsService;

    public PublicViewController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping({"", "/", "/layout"})
    public String layout() {
        return "layout";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new ClientsRqDto());
        }
        return "registerForm";
    }

    @PostMapping("/registerForm")
    public String handleRegisterForm(
            @Valid @ModelAttribute("user") ClientsRqDto clientsRqDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }

        try {
            clientsService.create(clientsRqDto);
            return "redirect:/v1/success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            redirectAttributes.addFlashAttribute("user", clientsRqDto);
            return "redirect:/v1/registerForm";
        }
    }
}