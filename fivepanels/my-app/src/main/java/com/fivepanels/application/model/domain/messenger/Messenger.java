package com.fivepanels.application.model.domain.messenger;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.BaseEntity;
import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.AssertionException;
import com.fivepanels.application.model.foundation.exception.MessengerException;
import com.fivepanels.application.model.repository.MessengerRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Messenger extends BaseEntity {

    private Set<Chat> chats;

    public Messenger() {
        super();
        this.chats = new LinkedHashSet<>();
        MessengerRepository.save(this);
    }

    public Messenger(LinkedHashSet<Chat> chats) {
        super();
        this.chats = chats;
        MessengerRepository.save(this);
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        Assertion.isNotNull(chat, "chat");
        chats.add(chat);
    }

    public void removeChat(Chat chat) {
        Assertion.isNotNull(chat, "chat");
        chats.remove(chat);
    }

    public Chat createGroupChat(String groupName, Set<User> members) {
        Assertion.isNotBlank(groupName, "groupName");
        Assertion.isNotNull(members, "members");

        if (members.size() < 3 || members.size() > 20) {
            throw new AssertionException("Group chat must have between 3 and 20 members. Given: " + members.size());
        }

        Chat chat = new Chat(groupName, members);
        addChat(chat);
        return chat;
    }

    public Chat createDirectChat(String chatName, Set<User> members) {
        Assertion.isNotBlank(chatName, "groupName");
        Assertion.isNotNull(members, "members");

        if (members.size() != 2) {
            throw new AssertionException("Direct chat must have exactly 2 members. Given: " + members.size());
        }

        Chat chat = new Chat(chatName, members);
        addChat(chat);
        return chat;
    }

    public void sendMessage(UUID chatId, Message message) {
        Assertion.isNotNull(chatId, "chatId");
        Assertion.isNotNull(message, "message");

        for (Chat chat : chats) {
            if (chat.getId().equals(chatId)) {
                chat.addMessage(message);
                return;
            }
        }

        throw new MessengerException("Chat not found with ID: " + chatId);
    }

    public void deleteMessage(UUID chatId, UUID messageId) {
        Assertion.isNotNull(chatId, "chatId");
        Assertion.isNotNull(messageId, "messageId");

        for (Chat chat : chats) {
            if (chat.getId().equals(chatId)) {
                chat.removeMessage(messageId);
                return;
            }
        }

        throw new AssertionException("Chat not found with ID: " + chatId);
    }

    public List<Message> showMessageHistory(UUID chatId) {
        Assertion.isNotNull(chatId, "chatId");
        for (Chat chat : chats) {
            if (chat.getId().equals(chatId)) {
                return chat.getMessageHistory();
            }
        }

        throw new AssertionException("Chat not found with ID: " + chatId);
    }
}