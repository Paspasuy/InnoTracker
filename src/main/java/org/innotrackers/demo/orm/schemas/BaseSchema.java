package org.innotrackers.demo.orm.schemas;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public class BaseSchema {
    @Id
    public String id;
    public Timestamp createdAt;
    public Timestamp updatedAt;

    public BaseSchema(String id, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public BaseSchema() {
    }
}
