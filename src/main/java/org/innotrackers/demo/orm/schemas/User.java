package org.innotrackers.demo.orm.schemas;

import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "workspace")
public class User extends BaseSchema {
    public String username;
    public String displayName;
    public String email;

    public void updateFields(User user) {
        username = user.username;
        displayName = user.displayName;
        email = user.email;
    }

//    public User(String id, Timestamp createdAt, Timestamp updatedAt, String username, String displayName, String email) {
//        super(id, createdAt, updatedAt);
//        this.username = username;
//        this.displayName = displayName;
//        this.email = email;
//    }

    public User() {
        super();
    }
}
