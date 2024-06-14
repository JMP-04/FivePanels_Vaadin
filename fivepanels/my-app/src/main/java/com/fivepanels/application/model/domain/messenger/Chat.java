package com.fivepanels.application.model.domain.messenger;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.BaseEntity;
import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.AssertionException;
import com.fivepanels.application.model.foundation.exception.MessengerException;
import com.fivepanels.application.model.repository.ChatRepository;

import java.time.Instant;
import java.util.*;

public class Chat extends BaseEntity {

    private String name;
    private Set<User> members;
    private List<Message> messageHistory;

    public Chat(String name, Set<User> members) {
        super();
        this.name = name;
        this.members = members;
        this.messageHistory = new ArrayList<>();
        ChatRepository.save(this);
    }

    public String getName() {
        return name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public void addMessage(Message message) {
        Assertion.isNotNull(message, "message");
        if (messageHistory.stream().anyMatch(m -> m.getId().equals(message.getId()))) {
            throw new MessengerException("Duplicate message ID: " + message.getId());
        }
        message.setId(UUID.randomUUID());
        message.setUpdatedAt(Instant.now());
        messageHistory.add(message);
    }

    public void removeMessage(UUID messageId) {
        boolean removed = messageHistory.removeIf(message -> message.getId().equals(messageId));
        if (!removed) {
            throw new AssertionException("Message not found with ID: " + messageId);
        }
    }

    public String showMessageHistory() {
        StringBuilder history = new StringBuilder();
        for (Message message : messageHistory) {
            history.append(message.toString()).append("\n");
        }
        return history.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}