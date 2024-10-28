package org.innotrackers.demo.orm.repos;

import org.innotrackers.demo.orm.schemas.Ticket;
import org.innotrackers.demo.orm.schemas.TicketSlim;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<TicketSlim, String> {

}
