package ru.covenant.code.landing.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.exceptions.ClientsNotFoundException;
import ru.covenant.code.landing.exceptions.InvalidClientsStatusException;
import ru.covenant.code.landing.service.ClientsService;

import java.util.UUID;

@Controller
@RequestMapping(value = "/admin/clients", produces = MediaType.TEXT_HTML_VALUE)
public class ViewController {

    private final ClientsService clientsService;

    public ViewController(ClientsService service) {
        this.clientsService = service;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String clientsPage(Model model,
                              @RequestParam(required = false) String success,
                              @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("clients", clientsService.getAllAdminClients());
        return "admin/clients";
    }

    @PostMapping("/{id}/delete")
    public String deleteClientFromPage(@PathVariable UUID id) {
        clientsService.delete(id);
        return "redirect:/admin/clients";
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String clientDetails(@PathVariable UUID id,
                                @RequestParam(required = false) String success, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("client", clientsService.getAdminClientById(id));
            model.addAttribute("success", success);
            return "admin/client-details";

        } catch (ClientsNotFoundException e) {
            ra.addFlashAttribute("error", "Заявка не найдена");
            return "redirect:/admin/clients";
        }
    }

    @PostMapping("/{id}/status")
    public String updateClientStatus(@PathVariable UUID id,
                                     @RequestParam Status status,
                                     RedirectAttributes ra) {
        try {
            ClientsStatusRqDto statusRqDto = new ClientsStatusRqDto();
            statusRqDto.setStatus(status);

            clientsService.updateStatus(id, statusRqDto);
            return "redirect:/admin/clients";
        } catch (ClientsNotFoundException e) {
            ra.addFlashAttribute("error", "Заявка не найдена");
            return "redirect:/admin/clients";
        } catch (InvalidClientsStatusException e) {
            ra.addFlashAttribute("error", "Невозможно изменить статус");
            return "redirect:/admin/clients";
        }
    }
}
