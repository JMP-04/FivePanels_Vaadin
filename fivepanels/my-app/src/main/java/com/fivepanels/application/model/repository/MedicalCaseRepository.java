package com.fivepanels.application.model.repository;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MedicalCaseRepository {

    private static final ConcurrentHashMap<UUID, MedicalCase> map = new ConcurrentHashMap<>();

    public static Optional<MedicalCase> findById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    public static List<MedicalCase> findAll() {
        return new ArrayList<>(map.values());
    }

    public static MedicalCase save(MedicalCase entity) {
        // Ensure entity ID is not null
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        map.put(entity.getId(), entity);
        entity.setCreatedAt(Instant.now());
        return entity;
    }

    public static int count() {
        return map.size();
    }

    public static void deleteById(UUID id) {
        map.remove(id);
    }

    public static boolean existsById(UUID id) {
        return map.containsKey(id);
    }
}
