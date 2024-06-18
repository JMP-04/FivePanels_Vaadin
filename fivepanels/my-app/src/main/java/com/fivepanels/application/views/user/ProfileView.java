package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.security.roles.Roles;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Profile")
@Route(value = "user/profile", layout = MainLayout.class)
@RolesAllowed(Roles.USER)
public class ProfileView extends VerticalLayout {

    private User user;

    public ProfileView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        user = VaadinSession.getCurrent().getAttribute(User.class);
    }

    private void addComponents() {
        if (user != null) {
            add(new H1("Profile"));
            add(new Paragraph("Name: " + user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName()));
            add(new Paragraph("Email: " + user.getEmail().getEmail()));
            add(new Paragraph("City: " + user.getUserProfile().getCity()));
        } else {
            add(new H1("No user logged in"));
        }
    }

    private void addListeners() {
        // Add any event listeners if needed
    }
}
