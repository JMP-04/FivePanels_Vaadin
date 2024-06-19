package com.fivepanels.application.views.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.fivepanels.application.views.MainLayout;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
public class HomeView extends Div {
    public HomeView() {
        setText("Welcome to the Home Page!");
    }
}