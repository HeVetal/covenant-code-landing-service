package ru.covenant.code.landing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.covenant.code.landing.dto.request.ClientsStatusRqDto;
import ru.covenant.code.landing.dto.response.ClientsAdminRsDto;
import ru.covenant.code.landing.entity.enumerated.Status;
import ru.covenant.code.landing.exceptions.ClientsNotFoundException;
import ru.covenant.code.landing.exceptions.InvalidClientsStatusException;
import ru.covenant.code.landing.service.ClientsService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(value = "/admin/clients", produces = MediaType.TEXT_HTML_VALUE)
public class PrivateViewController {

    private final ClientsService clientsService;

    public PrivateViewController(ClientsService service) {
        this.clientsService = service;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String clientsPage(Model model,
                              @RequestParam(required = false) String success,
                              @RequestParam(required = false) String error) {

        List<ClientsAdminRsDto> clients = clientsService.getAllAdminClients();

        // Статистика
        long totalCount = clients.size();
        long newCount = clients.stream()
                .filter(c -> c.getStatus() == Status.NEW)
                .count();
        long processedCount = clients.stream()
                .filter(c -> c.getStatus() == Status.PROCESSED)
                .count();

        // Заявки за сегодня
        OffsetDateTime startOfDay = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        long todayCount = clients.stream()
                .filter(c -> c.getCreatedAt() != null && c.getCreatedAt().isAfter(startOfDay))
                .count();

        // Передаем данные в модель
        model.addAttribute("error", error);
        model.addAttribute("success", success);
        model.addAttribute("clients", clients);
        model.addAttribute("clientsCount", totalCount);
        model.addAttribute("newCount", newCount);
        model.addAttribute("processedCount", processedCount);
        model.addAttribute("todayCount", todayCount);

        log.info("Admin page loaded: {} total, {} new, {} processed, {} today",
                totalCount, newCount, processedCount, todayCount);

        return "admin/clients";
    }

    @PostMapping("/{id}/delete")
    public String deleteClientFromPage(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            clientsService.delete(id);
            redirectAttributes.addAttribute("success", "deleted");
            log.info("Client deleted: {}", id);
        } catch (ClientsNotFoundException e) {
            redirectAttributes.addAttribute("error", "notfound");
            log.error("Client not found for deletion: {}", id);
        }
        return "redirect:/admin/clients";
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String clientDetails(@PathVariable UUID id,
                                @RequestParam(required = false) String success,
                                Model model,
                                RedirectAttributes ra) {
        try {
            ClientsAdminRsDto client = clientsService.getAdminClientById(id);
            model.addAttribute("client", client);
            model.addAttribute("success", success);
            log.info("Client details loaded: {}", id);
            return "admin/client-details";

        } catch (ClientsNotFoundException e) {
            ra.addFlashAttribute("error", "Заявка не найдена");
            log.error("Client not found: {}", id);
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
            log.info("Client status updated: {} -> {}", id, status);
            return "redirect:/admin/clients";

        } catch (ClientsNotFoundException e) {
            ra.addFlashAttribute("error", "Заявка не найдена");
            log.error("Client not found for status update: {}", id);
            return "redirect:/admin/clients";

        } catch (InvalidClientsStatusException e) {
            ra.addFlashAttribute("error", "Невозможно изменить статус");
            log.error("Invalid status transition for client: {}", id);
            return "redirect:/admin/clients";
        }
    }
}