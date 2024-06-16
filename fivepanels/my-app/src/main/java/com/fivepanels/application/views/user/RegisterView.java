package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.misc.Email;
import com.fivepanels.application.model.domain.user.misc.Password;
import com.fivepanels.application.model.repository.UserRepository;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Register")
@Route(value = "register", layout = MainLayout.class)
public class RegisterView extends VerticalLayout {

    private TextField firstNameField;
    private TextField lastNameField;
    private EmailField emailField;
    private PasswordField passwordField;
    private Button registerButton;

    public RegisterView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        emailField = new EmailField("Email");
        passwordField = new PasswordField("Password");
        registerButton = new Button("Register");
    }

    private void addComponents() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstNameField, lastNameField, emailField, passwordField, registerButton);
        add(new H1("Register"), formLayout);
    }

    private void addListeners() {
        registerButton.addClickListener(event -> {
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Notification.show("All fields are required.", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (UserRepository.findByEmail(email).isPresent()) {
                Notification.show("Email already registered.", 3000, Notification.Position.MIDDLE);
                return;
            }

            User user = new User(firstName, lastName, "Vienna", new Email(email), new Password(password.toCharArray()));
            UserRepository.save(user);
            Notification.show("Registration successful!");
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
    }
}
