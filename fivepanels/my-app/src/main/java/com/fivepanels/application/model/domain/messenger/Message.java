package com.fivepanels.application.model.domain.messenger;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.BaseEntity;
import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.AssertionException;

import java.io.File;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Message extends BaseEntity {
    private String content;
    private File file;
    private User sender;

    public Message(String content, User sender) {
        super();
        this.setId(UUID.randomUUID());
        setContent(content);
        setSender(sender);
    }

    public Message(File file, User sender) {
        super();
        this.setId(UUID.randomUUID());
        setFile(file);
        setSender(sender);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        Assertion.isNotNull(content, "content");
        Assertion.isNotBlank(content, "content");
        Assertion.hasMinLength(content, 1, "content");
        if (!content.equals(this.content)) {
            this.content = content;
            this.file = null;
            updatedAt = Instant.now();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        Assertion.isNotNull(file, "file");
        if (!file.exists() || !file.isFile()) {
            throw new AssertionException("File must exist and be a file");
        }
        this.file = file;
        this.content = null;
        updatedAt = Instant.now();
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        Assertion.isNotNull(sender, "sender");
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Message-Content: " + getContent() + " --- Message-File: " + getFile() + " --- Message-Sender: " + getSender();
    }
}