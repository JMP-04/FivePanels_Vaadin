package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.misc.Password;
import com.fivepanels.application.model.repository.UserRepository;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Profile")
@Route(value = "user/profile", layout = MainLayout.class)
public class ProfileView extends VerticalLayout {

    private LoginForm login;
    private User user;

    public ProfileView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        login = new LoginForm();
        login.setI18n(createLoginI18n());
    }

    private void addComponents() {
        add(login);
    }

    private void addListeners() {
        login.addLoginListener(event -> {
            String email = event.getUsername();
            char[] password = event.getPassword().toCharArray();

            UserRepository.findByEmail(email).ifPresentOrElse(storedUser -> {
                if (storedUser.getPassword().equals(new Password(password))) { // Use proper password hashing in a real application
                    Notification.show("Login successful");
                } else {
                    login.setError(true);
                }
            }, () -> login.setError(true));
        });
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Login");
        i18n.getForm().setUsername("Email");
        i18n.getForm().setPassword("Password");
        i18n.getForm().setSubmit("Login");
        i18n.getForm().setForgotPassword("Forgot password");
        i18n.getErrorMessage().setTitle("Incorrect email or password");
        i18n.getErrorMessage().setMessage("Check that you have entered the correct email and password and try again.");
        return i18n;
    }
}
