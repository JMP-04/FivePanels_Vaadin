package com.fivepanels.application.views.messenger;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Messenger")
@Route(value = "messenger/chats", layout = MainLayout.class)
public class MessengerView extends VerticalLayout {


}
