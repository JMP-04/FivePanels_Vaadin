package com.fivepanels.application.model.repository;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.misc.Email;
import com.fivepanels.application.model.domain.user.misc.Password;

import java.time.Instant;
import java.util.*;

public class UserRepository {

    private static final HashMap<UUID, User> map = new HashMap<>();

    public static Optional<User> findById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    public static List<User> findAll() {
        return new ArrayList<>(map.values());
    }

    public static User save(User entity) {
        map.put(entity.getId(), entity);
        entity.setUpdatedAt(Instant.now());
        return entity;
    }

    public static int count() {
        return map.size();
    }

    public static void deleteById(UUID id) {
        map.remove(id);
    }

    public static boolean existsById(UUID id) {
        return map.get(id) != null;
    }

    public static Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().getEmail().equals(email))
                .findFirst();
    }

    public static Optional<User> findByEmailAndPassword(String email, String password) {
        System.out.println("Attempting to find user by email and password.");
        return findAll().stream()
                .filter(user -> user.getEmail().getEmail().equals(email) && user.checkPassword(password.toCharArray()))
                .findFirst();
    }

    public static void seedTestUsers() {
        Password testPassword = new Password("TestPassword123!".toCharArray());
        User admin = new User("Admin", "User", "City", new Email("admin@example.com"), testPassword, "ADMIN");
        User regular = new User("Regular", "User", "City", new Email("user@example.com"), testPassword, "USER");

        System.out.println("Seeding users:");
        System.out.println("Admin: " + admin.getEmail().getEmail() + " Password: TestPassword123!");
        System.out.println("Regular: " + regular.getEmail().getEmail() + " Password: TestPassword123!");

        save(admin);
        save(regular);

        // Add each other as friends
        admin.addFriend(regular);
        regular.acceptFriendRequest(admin);

        save(admin);
        save(regular);
    }
}