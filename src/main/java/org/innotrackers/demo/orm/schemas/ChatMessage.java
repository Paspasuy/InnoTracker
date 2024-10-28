package org.innotrackers.demo.orm.schemas;

import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "workspace", name="chat_messages")
public class ChatMessage extends BaseSchema {
    public String chatId;
    public String senderId;
    public String message;
}
