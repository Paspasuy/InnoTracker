package org.innotrackers.demo.api.routers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.innotrackers.demo.orm.repos.ChatRepository;
import org.innotrackers.demo.orm.repos.UserRepository;
import org.innotrackers.demo.orm.schemas.ChatMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;


    ChatController(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }


    @Operation(summary = "Получить все сообщения в чате", description = "Использовать будем только на страничке конкретного тикета. id чата берется из детального тикета.")
    @GetMapping(path="/{chatId}", produces = "application/json")
    public ResponseEntity<List<ChatMessage>> getChat(@PathVariable String chatId) {
        List<ChatMessage> result = chatRepository.findAllByChatId(chatId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Отправить сообщение в чат", description = "Использовать будем только на страничке конкретного тикета. id чата берется из детального тикета.")
    @PostMapping(path="/{chatId}", produces = "application/json")
    public void sendMessage(@PathVariable String chatId, @RequestParam String message, HttpServletRequest request) {

        String senderId = null;

        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                senderId = userRepository.findFirstByUsername(username).id;
            }
        }

        ChatMessage msg = new ChatMessage();
        msg.chatId = chatId;
        msg.message = message;
        msg.senderId = senderId;
        chatRepository.save(msg);
    }
}
