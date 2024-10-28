package org.innotrackers.demo.orm.schemas;

import org.innotrackers.demo.api.schemas.TicketCreateUpdateParams;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Table(schema = "workspace")
public class Ticket extends TicketSlim {
    public TicketDetails details;

    public Ticket(String id, Timestamp createdAt, Timestamp updatedAt, String title, String sprint, String type, String status, String reporterId, String assigneeId, String[] tags, TicketDetails details, String parentId) {
        super(id, createdAt, updatedAt, title, sprint, type, status, reporterId, assigneeId, tags, parentId);
        this.details = details;
    }

    public Ticket() {
        super();
    }

    public Ticket(TicketCreateUpdateParams params) {
        super();
        this.details = new TicketDetails();
        this.details.chatId = UUID.randomUUID().toString();
        this.update(params);
    }

    public void update(TicketCreateUpdateParams params) {
        if (params.title != null) this.title = params.title;
        if (params.sprint != null) this.sprint = params.sprint;
        if (params.type != null) this.type = params.type;
        if (params.status != null) this.status = params.status;
        if (params.reporterId != null) this.reporterId = params.reporterId;
        if (params.assigneeId != null) this.assigneeId = params.assigneeId;
        if (params.tags != null) this.tags = params.tags;
        if (params.parentId != null) this.parentId = params.parentId;

        if (params.description != null) this.details.description = params.description;
        if (params.subtasks != null) this.details.subtasks = params.subtasks;

    }
}

