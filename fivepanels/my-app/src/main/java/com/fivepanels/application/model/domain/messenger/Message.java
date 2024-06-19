package com.fivepanels.application.model.domain.messenger;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.BaseEntity;

import java.time.Instant;

public class Message extends BaseEntity {

    private User sender;
    private String content;
    private Instant timestamp;

    public Message(User sender, String content, Instant now) {
        this.sender = sender;
        this.content = content;
        this.timestamp = Instant.now();
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}