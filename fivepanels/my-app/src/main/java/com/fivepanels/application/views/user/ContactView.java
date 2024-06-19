package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.UserProfile;
import com.fivepanels.application.model.domain.user.UserRelationship;
import com.fivepanels.application.model.domain.user.misc.Hashtag;
import com.fivepanels.application.model.domain.user.misc.Language;
import com.fivepanels.application.model.domain.user.misc.MedicalTitle;
import com.fivepanels.application.model.repository.UserRepository;
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
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Contacts")
@Route(value = "user/contacts", layout = MainLayout.class)
public class ContactView extends VerticalLayout {

    private Grid<User> friendsGrid;
    private List<User> friendsList;
    private Grid<User> allUsersGrid;
    private List<User> allUsersList;

    private User currentUser;

    public ContactView() {
        this.currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        friendsGrid = new Grid<>(User.class, false);
        friendsGrid.addColumn(user -> user.getUserProfile().getFirstName()).setHeader("First Name");
        friendsGrid.addColumn(user -> user.getUserProfile().getLastName()).setHeader("Last Name");
        friendsGrid.addColumn(user -> user.getUserProfile().getCity()).setHeader("City");
        friendsGrid.addColumn(new ComponentRenderer<>(user -> {
            HorizontalLayout actions = new HorizontalLayout();
            Button chatButton = new Button("Chat");
            Button removeButton = new Button("Remove");

            chatButton.addClickListener(event -> navigateToChatView(user));
            removeButton.addClickListener(event -> showRemoveConfirmationDialog(user));

            actions.add(chatButton, removeButton);
            return actions;
        })).setHeader("Actions");

        allUsersGrid = new Grid<>(User.class, false);
        allUsersGrid.addColumn(user -> user.getUserProfile().getFirstName()).setHeader("First Name");
        allUsersGrid.addColumn(user -> user.getUserProfile().getLastName()).setHeader("Last Name");
        allUsersGrid.addColumn(user -> user.getUserProfile().getCity()).setHeader("City");
        allUsersGrid.addColumn(new ComponentRenderer<>(user -> {
            Button addButton = new Button("Add Friend");
            addButton.addClickListener(event -> sendFriendRequest(user));
            return addButton;
        })).setHeader("Actions");

        refreshData();
    }

    private void addComponents() {
        add(new Span("Friends"), friendsGrid, new Span("All Users"), allUsersGrid);
    }

    private void addListeners() {
        friendsGrid.addItemClickListener(event -> showUserDetailsDialog(event.getItem().getUserProfile()));
    }

    private void navigateToChatView(User user) {
        Notification.show("Navigating to chat with " + user.getUserProfile().getFirstName());
        getUI().ifPresent(ui -> ui.navigate(MessengerView.class));
    }

    private void showRemoveConfirmationDialog(User user) {
        Dialog dialog = new Dialog();
        dialog.add(new Div(new Span("Are you sure you want to remove " + user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName() + "?")));

        Button confirmButton = new Button("Confirm", event -> {
            removeUser(user);
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
        dialog.add(buttons);

        dialog.open();
    }

    private void removeUser(User user) {
        currentUser.removeFriend(user);
        UserRepository.save(currentUser);
        UserRepository.save(user);

        refreshData();
        Notification.show("Removed " + user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void sendFriendRequest(User user) {
        currentUser.addFriend(user);
        UserRepository.save(currentUser);
        UserRepository.save(user);

        refreshData();
        Notification.show("Friend request sent to " + user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName(), 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void showUserDetailsDialog(UserProfile userProfile) {
        Dialog dialog = new Dialog();

        VerticalLayout layout = new VerticalLayout();
        layout.add(new Span("First Name: " + userProfile.getFirstName()));
        layout.add(new Span("Last Name: " + userProfile.getLastName()));
        layout.add(new Span("City: " + userProfile.getCity()));
        layout.add(new Span("Hashtags: " + userProfile.getExperiences().stream().map(Hashtag::getTag).reduce((tag1, tag2) -> tag1 + ", " + tag2).orElse("")));
        layout.add(new Span("Activity Score: " + userProfile.getActivityScore()));
        layout.add(new Span("Expert Score: " + userProfile.getExpertScore()));
        layout.add(new Span("Languages: " + userProfile.getLanguages().stream().map(Language::getLanguage).reduce((lang1, lang2) -> lang1 + ", " + lang2).orElse("")));
        layout.add(new Span("Medical Titles: " + userProfile.getMedicalTitles().stream().map(MedicalTitle::getMedicalTitle).reduce((title1, title2) -> title1 + ", " + title2).orElse("")));

        Button closeButton = new Button("Close", event -> dialog.close());
        layout.add(closeButton);

        dialog.add(layout);
        dialog.open();
    }

    private void refreshData() {
        friendsList = currentUser.getRelationships().entrySet().stream()
                .filter(entry -> entry.getValue() == UserRelationship.ESTABLISHED)
                .map(entry -> UserRepository.findById(entry.getKey()).orElse(null))
                .collect(Collectors.toList());

        friendsGrid.setItems(friendsList);

        allUsersList = UserRepository.findAll().stream()
                .filter(user -> !user.equals(currentUser) && !currentUser.getRelationships().containsKey(user.getId()))
                .collect(Collectors.toList());

        allUsersGrid.setItems(allUsersList);
    }
}