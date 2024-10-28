package org.innotrackers.demo.api.routers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.innotrackers.demo.orm.repos.UserRepository;
import org.innotrackers.demo.orm.schemas.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Получить всю информацию о пользователе")
    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> result = userRepository.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Изменить информацию о пользователе", description = "Изменяются только те поля, которые не нулевые. Username изменять нельзя. (Реализовавать фронтенд странички пользователя, видимо, не успеем. Забейте:P )")
    @PostMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<User> setUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> result = userRepository.findById(id);
        if (result.isEmpty()) {
            userRepository.save(user);
        } else {
            User existing = result.get();
            existing.updateFields(user);
            userRepository.save(existing);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Получить фото пользователя")
    @GetMapping(path="/{id}.jpeg", produces = "image/jpeg")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id) throws IOException {
        UUID.fromString(id); // Чтобы нельзя было подставить ../ и прочитать запрещенные файлы
        try {
            File f = new File(String.format("/pictures/%s.jpeg", id));
            byte[] bytes = Files.readAllBytes(f.toPath());
            return ResponseEntity.ok(bytes);
        } catch (java.nio.file.NoSuchFileException exception) {
            return null;
        }
    }

    @Operation(summary = "Изменить фото пользователя")
    @PostMapping(path="/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void setPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request, @SessionAttribute("requesterId") String requesterId) throws IOException {
//        String requesterId = null;
//
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            String username = (String) session.getAttribute("username");
//            if (username != null) {
//                requesterId = userRepository.findFirstByUsername(username).id;
//            }
//        }
        assert requesterId != null;
        File f = new File(String.format("/pictures/%s.jpeg", requesterId));
        Files.write(f.toPath(), file.getBytes());
    }
}
