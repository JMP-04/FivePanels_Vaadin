package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.UserProfile;
import com.fivepanels.application.model.domain.user.misc.Hashtag;
import com.fivepanels.application.model.domain.user.misc.Language;
import com.fivepanels.application.model.domain.user.misc.MedicalTitle;
import com.fivepanels.application.security.roles.Roles;
import com.fivepanels.application.views.MainLayout;
import com.fivepanels.application.views.messenger.MessengerView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PageTitle("Contacts")
@Route(value = "user/contacts", layout = MainLayout.class)
@RolesAllowed(Roles.USER)

public class ContactView extends VerticalLayout {

    private Grid<UserProfile> userGrid;
    private List<UserProfile> userList;

    public ContactView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        userGrid = new Grid<>(UserProfile.class, false);
        userGrid.addColumn(UserProfile::getFirstName).setHeader("First Name");
        userGrid.addColumn(UserProfile::getLastName).setHeader("Last Name");
        userGrid.addColumn(UserProfile::getCity).setHeader("City");
        userGrid.addColumn(userProfile ->
                userProfile.getExperiences().stream()
                        .map(Hashtag::getTag)
                        .reduce((tag1, tag2) -> tag1 + ", " + tag2)
                        .orElse("")
        ).setHeader("Hashtags");
        userGrid.addColumn(UserProfile::getActivityScore).setHeader("Activity Score");
        userGrid.addColumn(UserProfile::getExpertScore).setHeader("Expert Score");

        userGrid.addColumn(new ComponentRenderer<>(userProfile -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button chatButton = new Button("Chat");
            Button removeButton = new Button("Remove");

            chatButton.addClickListener(event -> navigateToChatView(userProfile));
            removeButton.addClickListener(event -> showRemoveConfirmationDialog(userProfile));

            actions.add(chatButton, removeButton);
            return actions;
        })).setHeader("Actions");

        userList = new ArrayList<>();
        userList.add(new UserProfile("John", "Doe", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("ACU"), new MedicalTitle("SURG")), List.of(new Hashtag("#Toxicology"), new Hashtag("#Dermatology"), new Hashtag("#Cardiology")), "New York", Set.of(new Language("German"))));
        userList.add(new UserProfile("Jane", "Smith", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("PED"), new MedicalTitle("NEU")), List.of(new Hashtag("#Neurology"), new Hashtag("#Dermatology")), "Berlin", Set.of(new Language("English"))));
        userList.add(new UserProfile("Emily", "Jones", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("CAR"), new MedicalTitle("DER")), List.of(new Hashtag("#Cardiology")), "Paris", Set.of(new Language("French"))));

        userGrid.setItems(userList);
    }

    private void addComponents() {
        add(userGrid);
    }

    private void addListeners() {
    }

    private void navigateToChatView(UserProfile userProfile) {
        // Implement navigation logic here
        Notification.show("Navigating to chat with " + userProfile.getFirstName());
        getUI().ifPresent(ui -> ui.navigate(MessengerView.class));
    }

    private void showRemoveConfirmationDialog(UserProfile userProfile) {
        Dialog dialog = new Dialog();
        dialog.add(new Div(new Span("Are you sure you want to remove " + userProfile.getFirstName() + " " + userProfile.getLastName() + "?")));

        Button confirmButton = new Button("Confirm", event -> {
            removeUser(userProfile);
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
        dialog.add(buttons);

        dialog.open();
    }

    private void removeUser(UserProfile userProfile) {
        userList.remove(userProfile);
        userGrid.setItems(userList);
        Notification.show("Removed " + userProfile.getFirstName() + " " + userProfile.getLastName(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}