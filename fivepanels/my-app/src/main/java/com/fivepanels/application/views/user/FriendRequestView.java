package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.domain.user.UserProfile;
import com.fivepanels.application.model.domain.user.UserRelationship;
import com.fivepanels.application.model.repository.UserRepository;
import com.fivepanels.application.views.MainLayout;
import com.fivepanels.application.views.messenger.MessengerView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@PageTitle("Friend Requests")
@Route(value = "user/friend-requests", layout = MainLayout.class)
public class FriendRequestView extends VerticalLayout {

    private Grid<User> incomingRequestsGrid;
    private Grid<User> outgoingRequestsGrid;
    private VerticalLayout incomingRequestsLayout;
    private VerticalLayout outgoingRequestsLayout;
    private HorizontalLayout splitLayout;
    private H2 incomingHeader;
    private H2 outgoingHeader;

    private List<User> incomingRequests;
    private List<User> outgoingRequests;
    private User currentUser;

    public FriendRequestView() {
        this.currentUser = getCurrentUser();

        this.incomingRequests = currentUser.getRelationships().entrySet().stream()
                .filter(entry -> entry.getValue() == UserRelationship.INCOMING)
                .map(entry -> UserRepository.findById(entry.getKey()).orElse(null))
                .collect(Collectors.toList());

        this.outgoingRequests = currentUser.getRelationships().entrySet().stream()
                .filter(entry -> entry.getValue() == UserRelationship.OUTGOING)
                .map(entry -> UserRepository.findById(entry.getKey()).orElse(null))
                .collect(Collectors.toList());

        initComponents();
        addComponents();
    }

    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent().getAttribute(User.class);
    }

    private void initComponents() {
        incomingRequestsGrid = new Grid<>();
        outgoingRequestsGrid = new Grid<>();

        incomingRequestsGrid.setItems(incomingRequests);
        outgoingRequestsGrid.setItems(outgoingRequests);

        incomingRequestsGrid.addColumn(user -> user.getUserProfile().getFirstName()).setHeader("First Name");
        incomingRequestsGrid.addColumn(user -> user.getUserProfile().getLastName()).setHeader("Last Name");
        incomingRequestsGrid.addColumn(user -> user.getUserProfile().getCity()).setHeader("City");
        incomingRequestsGrid.addColumn(new ComponentRenderer<>(user -> {
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            Button acceptButton = new Button("Accept", event -> acceptRequest(user));
            Button declineButton = new Button("Decline", event -> declineRequest(user));
            buttonsLayout.add(acceptButton, declineButton);
            return buttonsLayout;
        })).setHeader("Actions");

        outgoingRequestsGrid.addColumn(user -> user.getUserProfile().getFirstName()).setHeader("First Name");
        outgoingRequestsGrid.addColumn(user -> user.getUserProfile().getLastName()).setHeader("Last Name");
        outgoingRequestsGrid.addColumn(user -> user.getUserProfile().getCity()).setHeader("City");
        outgoingRequestsGrid.addColumn(new ComponentRenderer<>(user -> {
            Button cancelButton = new Button("Cancel Request", event -> cancelOutgoingRequest(user));
            return cancelButton;
        })).setHeader("Actions");

        incomingHeader = new H2("Incoming Requests");
        outgoingHeader = new H2("Outgoing Requests");

        refreshData();

        incomingRequestsLayout = new VerticalLayout(incomingHeader, incomingRequestsGrid);
        outgoingRequestsLayout = new VerticalLayout(outgoingHeader, outgoingRequestsGrid);

        splitLayout = new HorizontalLayout(incomingRequestsLayout, outgoingRequestsLayout);
        splitLayout.setSizeFull();
        splitLayout.setFlexGrow(1, incomingRequestsLayout, outgoingRequestsLayout);
    }

    private void addComponents() {
        add(splitLayout);
        setSizeFull();
    }

    private void acceptRequest(User user) {
        currentUser.acceptFriendRequest(user);
        UserRepository.save(currentUser);
        UserRepository.save(user);

        refreshData();
        Notification.show("Accepted friend request from: " + user.getUserProfile().getFirstName(), 3000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        navigateToChatView(user);
    }

    private void declineRequest(User user) {
        currentUser.declineFriendRequest(user);
        UserRepository.save(currentUser);
        UserRepository.save(user);

        refreshData();
        Notification.show("Declined friend request from: " + user.getUserProfile().getFirstName(), 3000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void cancelOutgoingRequest(User user) {
        currentUser.removeFriend(user);
        UserRepository.save(currentUser);
        UserRepository.save(user);

        refreshData();
        Notification.show("Canceled friend request to: " + user.getUserProfile().getFirstName(), 3000, Notification.Position.TOP_CENTER)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void refreshData() {
        incomingRequests = currentUser.getRelationships().entrySet().stream()
                .filter(entry -> entry.getValue() == UserRelationship.INCOMING)
                .map(entry -> UserRepository.findById(entry.getKey()).orElse(null))
                .collect(Collectors.toList());

        outgoingRequests = currentUser.getRelationships().entrySet().stream()
                .filter(entry -> entry.getValue() == UserRelationship.OUTGOING)
                .map(entry -> UserRepository.findById(entry.getKey()).orElse(null))
                .collect(Collectors.toList());

        incomingRequestsGrid.setItems(incomingRequests);
        outgoingRequestsGrid.setItems(outgoingRequests);
    }

    private void navigateToChatView(User friend) {
        Notification.show("Navigating to chat with " + friend.getUserProfile().getFirstName());
        getUI().ifPresent(ui -> ui.navigate(MessengerView.class));
    }
}