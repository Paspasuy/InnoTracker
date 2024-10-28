package org.innotrackers.demo.utils;

import org.innotrackers.demo.api.schemas.TicketCreateUpdateParams;
import org.innotrackers.demo.orm.repos.ChatRepository;
import org.innotrackers.demo.orm.repos.DetailedTicketRepository;
import org.innotrackers.demo.orm.repos.UserRepository;
import org.innotrackers.demo.services.TicketService;
import org.innotrackers.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailedTicketRepository detailedTicketRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;

    public void populateDatabase() {

        // Populate users
        String u1 = "stivs";
        String u2 = "l.torvalds";
        String u3 = "vladimir";

        userService.createUser(u1, "Джоб Стивс", "j.stivs@innotracker.org", "123123", false);
        userService.createUser(u2, "Линус Торвальдс", "l.torvalds@innotracker.org", "password", false);
        userService.createUser(u3, "Владимир Владимирович", "v.v.putin@kremlin.ru", "password", true);

        String uid1 = userRepository.findFirstByUsername(u1).id;
        String uid2 = userRepository.findFirstByUsername(u2).id;
        String uid3 = userRepository.findFirstByUsername(u3).id;

        // Populate tickets

        ticketService.createTicket(new TicketCreateUpdateParams(
                "Запилить фронт на реакте",
                "23_09_2024",
                "TASK",
                "IN_PROGRESS",
                uid2,
                uid1,
                new String[]{"Frontend Stream", "Design"},
                "This is very long task description. \n Это __очень__ длинное описание задачи.",
                null,
                null
        ));

        String parentTaskId = ticketService.createTicket(new TicketCreateUpdateParams(
                "Доделать бекенд",
                "23_09_2024",
                "FEATURE",
                "IN_PROGRESS",
                uid3,
                uid2,
                new String[]{"Backend Stream"},
                "Нужно доделать бекенд, чтобы трекером можно было пользоваться",
                new String[]{},
                null
        ));

        String subtaskId1 = ticketService.createTicket(new TicketCreateUpdateParams(
                "Проверка полей тикета",
                "23_09_2024",
                "TASK",
                "TODO",
                uid2,
                uid2,
                new String[]{"Backend Stream", "Security"},
                "Обработка неверных полей при создании тикета. \n Надо обработать случаи когда в api даются неверные значения строк.",
                null,
                parentTaskId
        ));

        String subtaskId2 = ticketService.createTicket(new TicketCreateUpdateParams(
                "Пояснения к эндпоинтам",
                "23_09_2024",
                "TASK",
                "REVIEW",
                uid3,
                uid3,
                new String[]{"Backend Stream", "Security", "TechDebt"},
                "Написать документацию и добавить докстринги к эндпоинтам. \n **User story**: как пользователь API, я хочу понимать, как пользоваться API.",
                null,
                parentTaskId
        ));
        ticketService.createTicket(new TicketCreateUpdateParams(
                "Задача за которую никто не взялся",
                "23_09_2024",
                "TASK",
                "NEW",
                uid2,
                null,
                new String[]{"Backend Stream", "Security"},
                "Пожалуйста возьмитесь кто-нибудь, очень нужно завершить к концу хакатона",
                null,
                parentTaskId
        ));
        ticketService.createTicket(new TicketCreateUpdateParams(
                "Очень жаль",
                "23_09_2024",
                "TASK",
                "NEW",
                uid1,
                uid1,
                new String[]{"Frontend Stream", "TechDebt"},
                "Задача, которую выкинули в *помойку*",
                null,
                null
        ));
    }

    public void cleanDatabase() {
        userRepository.deleteAll();
        detailedTicketRepository.deleteAll();
        chatRepository.deleteAll();
        System.out.println("Database cleaned successfully.");
    }
}
