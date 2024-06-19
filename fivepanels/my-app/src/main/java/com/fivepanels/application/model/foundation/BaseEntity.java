package com.fivepanels.application.model.foundation;

import java.time.Instant;
import java.util.UUID;

public abstract class BaseEntity {

    private UUID id;
    protected Instant createdAt;
    protected Instant updatedAt;

    public BaseEntity() {

        setId(UUID.randomUUID());
        setCreatedAt(Instant.now());
        setUpdatedAt(Instant.now());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}