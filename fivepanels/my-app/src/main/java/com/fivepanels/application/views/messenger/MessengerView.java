package com.fivepanels.application.views.messenger;

import com.fivepanels.application.model.domain.messenger.Chat;
import com.fivepanels.application.model.domain.messenger.Message;
import com.fivepanels.application.model.domain.messenger.Messenger;
import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Messenger")
@Route(value = "messenger/chats", layout = MainLayout.class)
public class MessengerView extends HorizontalLayout {

    private ListBox<Chat> chatListBox;
    private Messenger messenger;
    private MessageList messageList;
    private MessageInput messageInput;
    private User currentUser;
    private Chat selectedChat;

    public MessengerView() {
        this.currentUser = VaadinSession.getCurrent().getAttribute(User.class);
        initComponents();
        addComponents();
        addListeners();
        loadChats();
    }

    private void initComponents() {
        chatListBox = new ListBox<>();
        chatListBox.setItemLabelGenerator(Chat::getName);

        messageList = new MessageList();

        messageInput = new MessageInput();

        HorizontalLayout chatLayout = new HorizontalLayout(chatListBox, messageList, messageInput);
        chatLayout.setSizeFull();
        chatLayout.setFlexGrow(1, chatListBox);
        chatLayout.setFlexGrow(2, messageList);

        add(chatLayout);
        setSizeFull();
    }

    private void addComponents() {
        VerticalLayout rightPanel = new VerticalLayout(messageList, messageInput);
        rightPanel.setPadding(true);
        rightPanel.setSizeFull();
        add(chatListBox, rightPanel);
        setFlexGrow(1, chatListBox);
        setFlexGrow(2, rightPanel);
    }

    private void addListeners() {
        chatListBox.addValueChangeListener(event -> {
            selectedChat = event.getValue();
            if (selectedChat != null) {
                loadMessages(selectedChat);
            }
        });

        messageInput.addSubmitListener(event -> {
            if (selectedChat != null) {
                Message message = new Message(currentUser, event.getValue(), Instant.now());
                selectedChat.addMessage(message);
                loadMessages(selectedChat);
            }
        });
    }

    private void loadChats() {
        messenger = currentUser.getMessenger();
        Set<Chat> chats = messenger.getChats();
        chatListBox.setItems(chats);
        if (!chats.isEmpty()) {
            chatListBox.setValue(chats.iterator().next());
        }
    }

    private void loadMessages(Chat chat) {
        messageList.setItems(chat.getMessageHistory().stream().map(this::convertToMessageListItem).collect(Collectors.toList()));
    }

    private MessageListItem convertToMessageListItem(Message message) {
        return new MessageListItem(
                message.getContent(),
                message.getTimestamp(),
                message.getSender().getUserProfile().getFirstName()
        );
    }
}
