package org.innotrackers.demo.api.schemas;

public class TicketCreateUpdateParams {
    public String title;
    public String sprint;
    public String type;
    public String status;
    public String reporterId;
    public String assigneeId;
    public String[] tags;

    public String description;
    public String[] subtasks;
    public String parentId;

    public TicketCreateUpdateParams(String title, String sprint, String type, String status, String reporterId, String assigneeId, String[] tags, String description, String[] subtasks, String parentId) {
        this.title = title;
        this.sprint = sprint;
        this.type = type;
        this.status = status;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.tags = tags;
        this.description = description;
        this.subtasks = subtasks;
        this.parentId = parentId;
        if (this.subtasks == null) {
            this.subtasks = new String[]{};
        }
        if (this.tags == null) {
            this.subtasks = new String[]{};
        }
    }
}
