package org.innotrackers.demo.api.routers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.innotrackers.demo.api.schemas.TicketCreateUpdateParams;
import org.innotrackers.demo.orm.repos.DetailedTicketRepository;
import org.innotrackers.demo.orm.repos.FilterTicketRepository;
import org.innotrackers.demo.orm.repos.TicketRepository;
import org.innotrackers.demo.orm.repos.UserRepository;
import org.innotrackers.demo.orm.schemas.TicketDetails;
import org.innotrackers.demo.orm.schemas.Ticket;
import org.innotrackers.demo.orm.schemas.TicketSlim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    private final DetailedTicketRepository detailedTickets;
    @Autowired
    private final TicketRepository tickets;
    @Autowired
    private final FilterTicketRepository filterTickets;
    @Autowired
    private final UserRepository userRepository;

    TicketController(DetailedTicketRepository detailedTickets, TicketRepository tickets, FilterTicketRepository filterTickets, UserRepository userRepository) {
        this.detailedTickets = detailedTickets;
        this.tickets = tickets;
        this.filterTickets = filterTickets;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Детальный тикет", description = "Получить всю информацию о тикете, включая описание, сабтаски и chatId")
    @GetMapping(path="/detailed/{id}", produces = "application/json")
    public ResponseEntity<Ticket> findDetailedTicket(@PathVariable String id) {
        Optional<Ticket> result = detailedTickets.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Получить тикеты по фильтрам", description = "Использовать на страницах kanban-доски и списка тикетов. Можно фильтровать по набору тегов, и по тому, принадлежит ли задача самому пользователю доски.")
    @GetMapping(path="/filter", produces = "application/json")
    public ResponseEntity<List<TicketSlim>> filterTickets(@RequestParam(required = false) List<String> tags, @RequestParam boolean onlyMyIssues, HttpServletRequest request) {

        String requesterId = null;

        if (onlyMyIssues) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute("username");
                if (username != null) {
                    requesterId = userRepository.findFirstByUsername(username).id;
                }
            }
        }

        System.out.println("CSRF Token: " + ((CsrfToken)request.getAttribute("_csrf")).getToken());

        if (tags == null) {
            tags = List.of();
        }
        List<TicketSlim> result = filterTickets.filterTickets(tags, requesterId, onlyMyIssues);
        for (TicketSlim ticket: result) {
            System.out.println(ticket.id);
            System.out.println(ticket.assigneeId);
            System.out.println(ticket.reporterId);
        }
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Отфильтровать тикеты", description = "Использовать для того, чтобы получать сразу много тикетов по айдишником. Использовать в странице конкретного тикета, чтобы отобразить список сабтасок тикета.")
    @GetMapping(path="/manyTickets/{ids}", produces = "application/json")
    public ResponseEntity<List<TicketSlim>> findManyTickets(@PathVariable String[] ids) {
        List<TicketSlim> tickets1 = new ArrayList<>();
        for (String id: ids) {
            Optional<TicketSlim> result = tickets.findById(id);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            tickets1.add(result.get());
        }
        return ResponseEntity.ok(tickets1);
    }

    @Operation(summary = "Создать тикет", description = "Создает тикет.\n Обязательные поля: title, requesterId. Если не передать любое необязательное поле, то вместо него в базе будет лежать null.")
    @PostMapping(path="/", produces = "application/json")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketCreateUpdateParams params) {
        Ticket ticket = new Ticket(params);
        detailedTickets.save(ticket);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Изменить тикет", description = "Изменяет те поля тикета, которые указаны")
    @PostMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody TicketCreateUpdateParams params) {
        Optional<Ticket> result = detailedTickets.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            Ticket existing = result.get();
            existing.update(params);
            detailedTickets.save(existing);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Изменить статус", description = "Использовать при перетаскивании тикета по доске между колонками! Отдельный эндпоинт для изменения только статуса.")
    @PostMapping(path="/{id}/status", produces = "application/json")
    public ResponseEntity<Ticket> updateStatus(@PathVariable String id, @RequestBody String newStatus) {
        Optional<TicketSlim> result = tickets.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            TicketSlim existing = result.get();
            existing.status = newStatus;
            tickets.save(existing);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
