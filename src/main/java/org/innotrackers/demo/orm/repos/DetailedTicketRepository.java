package org.innotrackers.demo.orm.repos;

import org.innotrackers.demo.orm.schemas.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedTicketRepository extends CrudRepository<Ticket, String> {

}
