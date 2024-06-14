package com.fivepanels.application.views.home;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home | FivePanels")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private H1 welcomeTitle;
    private H2 noticeMessage;
    private LoginForm loginForm;

    public HomeView() {

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        welcomeTitle = new H1("Welcome to FivePanels");
        noticeMessage = new H2("Please login to proceed.");
        loginForm = new LoginForm();
    }

    private void addComponents() {

        setMargin(true);
        add(welcomeTitle, noticeMessage, loginForm);

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, loginForm);

        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, welcomeTitle);
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, noticeMessage);
    }

    private void addListeners() {

    }
}
