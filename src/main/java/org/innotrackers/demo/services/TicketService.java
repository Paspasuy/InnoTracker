package org.innotrackers.demo.services;

import org.innotrackers.demo.api.schemas.TicketCreateUpdateParams;
import org.innotrackers.demo.orm.repos.DetailedTicketRepository;
import org.innotrackers.demo.orm.repos.TicketRepository;
import org.innotrackers.demo.orm.schemas.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    DetailedTicketRepository detailedTicketRepository;

    public String createTicket(
        TicketCreateUpdateParams params
    ) {
        Ticket ticket = new Ticket(params);
        if (ticket.parentId != null) {
            Optional<Ticket> parentMaybe = detailedTicketRepository.findById(ticket.parentId);
            if (parentMaybe.isEmpty()) {
                throw new RuntimeException("Wrong parent id when creating subtask");
            }

            Ticket parent = parentMaybe.get();
            List<String> subtasks = new ArrayList<>(Arrays.asList(parent.details.subtasks));
            subtasks.add(ticket.id);
            parent.details.subtasks = subtasks.toArray(new String[0]);

            detailedTicketRepository.saveAll(List.of(parent, ticket));
        } else {
            detailedTicketRepository.save(ticket);
        }
        return ticket.id;
    }
}
