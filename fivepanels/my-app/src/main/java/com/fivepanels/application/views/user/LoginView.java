package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.misc.Password;
import com.fivepanels.application.model.repository.UserRepository;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Optional;

@PageTitle("Login")
@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

    private EmailField emailField;
    private PasswordField passwordField;
    private Button loginButton;

    public LoginView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        emailField = new EmailField("Email");
        passwordField = new PasswordField("Password");
        loginButton = new Button("Login");
    }

    private void addComponents() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(emailField, passwordField, loginButton);
        add(new H1("Login"), formLayout);
    }

    private void addListeners() {
        loginButton.addClickListener(event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            if (email.isEmpty() || password.isEmpty()) {
                Notification.show("Email and password are required.", 3000, Notification.Position.MIDDLE);
                return;
            }

            Optional<User> userOptional = UserRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(new Password(password.toCharArray()))) { // Use the correct password comparison
                    VaadinSession.getCurrent().setAttribute(User.class, user);
                    Notification.show("Login successful!", 3000, Notification.Position.MIDDLE);
                    getUI().ifPresent(ui -> ui.navigate("user/profile"));
                } else {
                    Notification.show("Invalid email or password.", 3000, Notification.Position.MIDDLE);
                }
            } else {
                Notification.show("Invalid email or password.", 3000, Notification.Position.MIDDLE);
            }
        });
    }
}
