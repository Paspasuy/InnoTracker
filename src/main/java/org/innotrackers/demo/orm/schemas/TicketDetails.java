package org.innotrackers.demo.orm.schemas;

import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "workspace", name = "ticket_details")
public class TicketDetails {
    public String description;
    public String[] subtasks;
    public String chatId;

    public TicketDetails(String description, String[] subtasks, String chatId) {
        this.description = description;
        this.subtasks = subtasks;
        this.chatId = chatId;
        if (subtasks == null) {
            this.subtasks = new String[]{};
        }
    }

    public TicketDetails() {
    }
}
