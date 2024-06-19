package com.fivepanels.application.views.user;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.fivepanels.application.views.MainLayout;

@PageTitle("Admin Dashboard")
@Route(value = "admin/dashboard", layout = MainLayout.class)
public class AdminDashboardView extends Div {
    public AdminDashboardView() {
        setText("Welcome to the Admin Dashboard!");
    }
}