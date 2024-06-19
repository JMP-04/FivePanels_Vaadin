package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.repository.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.Optional;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

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

            System.out.println("Login attempt with Email: " + email + " Password: " + password);

            Optional<User> userOptional = UserRepository.findByEmailAndPassword(email, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                VaadinSession.getCurrent().setAttribute(User.class, user);
                Notification.show("Login successful!", 3000, Notification.Position.MIDDLE);

                System.out.println("Login successful for user: " + user.getEmail().getEmail());
                System.out.println("User role: " + user.getRole());

                if ("ADMIN".equals(user.getRole())) {
                    getUI().ifPresent(ui -> ui.navigate("admin/dashboard"));
                } else {
                    getUI().ifPresent(ui -> ui.navigate("user/profile"));
                }
            } else {
                System.out.println("Login failed for Email: " + email);
                Notification.show("Invalid email or password.", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);
        if (currentUser != null) {
            if ("ADMIN".equals(currentUser.getRole())) {
                event.forwardTo("admin/dashboard");
            } else {
                event.forwardTo("user/profile");
            }
        }
    }
}