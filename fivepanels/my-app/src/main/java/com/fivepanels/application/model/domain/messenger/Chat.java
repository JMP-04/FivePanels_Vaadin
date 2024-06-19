package com.fivepanels.application.model.domain.messenger;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Chat extends BaseEntity {

    private UUID id;
    private String name;
    private Set<User> members;
    private List<Message> messageHistory;

    public Chat(String name, Set<User> members) {
        super();
        this.name = name;
        this.members = members;
        this.messageHistory = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    public void addMessage(Message message) {
        this.messageHistory.add(message);
    }

    public void removeMessage(UUID messageId) {
        messageHistory.removeIf(message -> message.getId().equals(messageId));
    }

    @Override
    public String toString() {
        return name;
    }
}