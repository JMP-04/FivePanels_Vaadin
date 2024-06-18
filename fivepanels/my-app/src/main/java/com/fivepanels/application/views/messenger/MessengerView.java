package com.fivepanels.application.views.messenger;

import com.fivepanels.application.model.domain.messenger.Chat;
import com.fivepanels.application.model.domain.messenger.Message;
import com.fivepanels.application.model.domain.messenger.Messenger;
import com.fivepanels.application.security.roles.Roles;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@PageTitle("Messenger")
@Route(value = "messenger/chats", layout = MainLayout.class)
@RolesAllowed(Roles.USER)

public class MessengerView extends HorizontalLayout {

    private ListBox<Chat> chatListBox;
    private Messenger messenger;
    private MessageList messageList;

    public MessengerView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        messenger = new Messenger(); // Beispielinitialisierung, durch Ihre Logik ersetzen

        // Initialisiere ListBox für Chats
        chatListBox = new ListBox<>();
        chatListBox.setItemLabelGenerator(Chat::toString);
        messageList = new MessageList();

        // Setze ListBox-Elemente oder Nachricht, falls keine Chats vorhanden sind
        Set<Chat> chats = messenger.getChats();
        if (chats.isEmpty()) {
            Span noChatsMessage = new Span("You have no current chats!");
            VerticalLayout messageLayout = new VerticalLayout(noChatsMessage);
            messageLayout.setSizeFull();
            messageLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            messageLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            // Zentriere horizontal innerhalb von MessengerView
            HorizontalLayout centerLayout = new HorizontalLayout(messageLayout);
            centerLayout.setSizeFull();
            centerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            centerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
            add(centerLayout);
        } else {
            chatListBox.setItems(chats);
            HorizontalLayout leftPanel = new HorizontalLayout(chatListBox);
            leftPanel.setPadding(true);
            add(leftPanel);

            VerticalLayout rightPanel = new VerticalLayout(messageList);
            rightPanel.setPadding(true);
            add(rightPanel);
        }
    }

    private void addComponents() {
    }

    private void addListeners() {
        chatListBox.addValueChangeListener(event -> {
            Chat selectedChat = event.getValue();
            if (selectedChat != null) {
                loadMessages(selectedChat);
            }
        });
    }

    private void loadMessages(Chat chat) {
        Set<Message> messageHistory = new LinkedHashSet<>(chat.getMessageHistory());
        messageList.setItems((MessageListItem) messageHistory);
    }
}
