package org.innotrackers.demo.orm.repos;

import org.innotrackers.demo.orm.schemas.ChatMessage;
import org.innotrackers.demo.orm.schemas.TicketSlim;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<ChatMessage, String> {
    List<ChatMessage> findAllByChatId(String chatId);
}
