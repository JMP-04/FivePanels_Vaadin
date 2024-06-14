package com.fivepanels.application.model.repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    public void save(T baseEntity);

    public void delete(T baseEntity);

    public void deleteById(UUID uuid);

    public int count();

    public boolean idDoesExist(UUID uuid);

    public T findById(UUID id);

    public List<T> findAll();
}
