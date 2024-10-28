package org.innotrackers.demo.orm.schemas;

import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Table(schema = "workspace", name = "ticket")
public class TicketSlim extends BaseSchema {
    public String title;
    public String sprint;
    public String type;
    public String status;
    public String reporterId;
    public String assigneeId;
    public String[] tags;
    public String parentId;

    public TicketSlim(String id, Timestamp createdAt, Timestamp updatedAt, String title, String sprint, String type, String status, String reporterId, String assigneeId, String[] tags, String parentId) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.sprint = sprint;
        this.type = type;
        this.status = status;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.tags = tags;
        this.parentId = parentId;
    }

    public TicketSlim() {
        super();
    }

}
