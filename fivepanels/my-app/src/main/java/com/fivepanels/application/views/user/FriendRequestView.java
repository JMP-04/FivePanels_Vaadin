package com.fivepanels.application.views.user;

import com.fivepanels.application.views.MainLayout;
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

@PageTitle("Friend Requests")
@Route(value = "user/friend-requests", layout = MainLayout.class)
public class FriendRequestView extends VerticalLayout {

    private Grid<String> incomingRequestsGrid;
    private Grid<String> outgoingRequestsGrid;
    private VerticalLayout incomingRequestsLayout;
    private VerticalLayout outgoingRequestsLayout;
    private HorizontalLayout splitLayout;
    private H2 incomingHeader;
    private H2 outgoingHeader;

    public FriendRequestView() {
        initComponents();
        addComponents();
    }

    private void initComponents() {
        // Create grids for incoming and outgoing requests
        incomingRequestsGrid = new Grid<>();
        outgoingRequestsGrid = new Grid<>();

        // Example data for demonstration
        incomingRequestsGrid.setItems("Alice", "Bob", "Charlie");
        outgoingRequestsGrid.setItems("David", "Eve", "Frank");

        // Create custom headers
        incomingHeader = new H2("Incoming Requests");
        incomingHeader.getStyle().set("color", "green");

        outgoingHeader = new H2("Outgoing Requests");
        outgoingHeader.getStyle().set("color", "red");

        // Add headers to the grids
        incomingRequestsGrid.addColumn(name -> name).setHeader(incomingHeader);
        outgoingRequestsGrid.addColumn(name -> name).setHeader(outgoingHeader);

        // Add buttons to each row in the incoming requests grid
        incomingRequestsGrid.addColumn(new ComponentRenderer<>(item -> {
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            Button acceptButton = new Button("Accept", event -> acceptRequest(item));
            Button declineButton = new Button("Decline", event -> declineRequest(item));
            acceptButton.getStyle().set("background-color", "green");
            acceptButton.getStyle().set("color", "white");
            declineButton.getStyle().set("background-color", "red");
            declineButton.getStyle().set("color", "white");
            buttonsLayout.add(acceptButton, declineButton);
            return buttonsLayout;
        })).setHeader(createStyledHeader("Actions"));

        // Add buttons to each row in the outgoing requests grid
        outgoingRequestsGrid.addColumn(new ComponentRenderer<>(item -> {
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            Button cancelButton = new Button("Cancel friend request", event -> cancelOutgoingRequest(item));
            cancelButton.getStyle().set("background-color", "red");
            cancelButton.getStyle().set("color", "white");
            buttonsLayout.add(cancelButton);
            return buttonsLayout;
        })).setHeader(createStyledHeader("Actions"));

        incomingRequestsLayout = new VerticalLayout(incomingRequestsGrid);
        incomingRequestsLayout.setSizeFull();

        outgoingRequestsLayout = new VerticalLayout(outgoingRequestsGrid);
        outgoingRequestsLayout.setSizeFull();

        // Create a horizontal layout to hold the two vertical layouts
        splitLayout = new HorizontalLayout(incomingRequestsLayout, outgoingRequestsLayout);
        splitLayout.setSizeFull();
        splitLayout.setFlexGrow(1, incomingRequestsLayout, outgoingRequestsLayout);
    }

    private void addComponents() {
        // Add the horizontal layout to the main layout
        add(splitLayout);
        setSizeFull();
    }

    private Div createStyledHeader(String text) {
        Div header = new Div();
        header.setText(text);
        header.getStyle().set("color", "grey");
        header.getStyle().set("font-weight", "bold");
        header.getStyle().set("font-size", "var(--lumo-font-size-l)");
        return header;
    }

    // Placeholder methods for button actions
    private void acceptRequest(String name) {
        // Implement the accept request logic here
        Notification notification = Notification.show("Accepted friend request from: " + name);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void declineRequest(String name) {
        // Implement the decline request logic here
        Notification notification = Notification.show("Declined friend request from: " + name);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    private void cancelOutgoingRequest(String name) {
        // Implement the cancel outgoing request logic here
        Notification notification = Notification.show("Canceled friend request to: " + name);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
