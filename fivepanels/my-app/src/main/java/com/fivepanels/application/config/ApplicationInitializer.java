package com.fivepanels.application.config;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.misc.Email;
import com.fivepanels.application.model.domain.user.misc.Password;
import com.fivepanels.application.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationInitializer {

    @Bean
    CommandLineRunner init() {
        return args -> {
            seedTestUsers();
        };
    }

    private void seedTestUsers() {
        if (UserRepository.count() == 0) {
            Password testPassword = new Password("TestPassword123!".toCharArray());
            User admin = new User("Admin", "User", "City", new Email("admin@example.com"), testPassword, "ADMIN");
            User regular = new User("Regular", "User", "City", new Email("user@example.com"), testPassword, "USER");

            System.out.println("Seeding users:");
            System.out.println("Admin: " + admin.getEmail().getEmail() + " Password: TestPassword123!");
            System.out.println("Regular: " + regular.getEmail().getEmail() + " Password: TestPassword123!");

            UserRepository.save(admin);
            UserRepository.save(regular);

            // Add each other as friends
            admin.addFriend(regular);
            regular.acceptFriendRequest(admin);
            admin.createDirectChat(regular);

            UserRepository.save(admin);
            UserRepository.save(regular);

            // Verify the seeding
            System.out.println("Admin user saved: " + UserRepository.findByEmail("admin@example.com").isPresent());
            System.out.println("Regular user saved: " + UserRepository.findByEmail("user@example.com").isPresent());
        }
    }
}