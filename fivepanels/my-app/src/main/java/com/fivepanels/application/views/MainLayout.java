package com.fivepanels.application.views;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.views.about.AboutView;
import com.fivepanels.application.views.home.HomeView;
import com.fivepanels.application.views.medicalcase.CreateNewMedicalCaseView;
import com.fivepanels.application.views.medicalcase.MedicalCaseView;
import com.fivepanels.application.views.medicalcase.MyMedicalCaseView;
import com.fivepanels.application.views.messenger.MessengerView;
import com.fivepanels.application.views.user.ContactView;
import com.fivepanels.application.views.user.FriendRequestView;
import com.fivepanels.application.views.user.ProfileView;
import com.fivepanels.application.views.user.AdminDashboardView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class MainLayout extends AppLayout implements BeforeEnterObserver {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
        addLogoutButton();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        HorizontalLayout headerLayout = new HorizontalLayout(toggle, viewTitle);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setWidthFull();

        addToNavbar(true, headerLayout);
    }

    private void addDrawerContent() {
        Span appName = new Span("FivePanels");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE, LumoUtility.TextColor.PRIMARY);
        Header header = new Header(appName);

        VerticalLayout navigation = createNavigation();

        addToDrawer(header, navigation, createFooter());
    }

    private VerticalLayout createNavigation() {
        VerticalLayout layout = new VerticalLayout();

        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);
        if (currentUser != null) {
            layout.add(createSectionLabel("Home"));
            layout.add(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

            layout.add(createSectionLabel("About"));
            layout.add(new SideNavItem("About", AboutView.class, LineAwesomeIcon.INFO_SOLID.create()));

            layout.add(createSectionLabel("User"));
            layout.add(new SideNavItem("Profile", ProfileView.class, LineAwesomeIcon.USER_SOLID.create()));
            layout.add(new SideNavItem("Friend Requests", FriendRequestView.class, LineAwesomeIcon.PEOPLE_CARRY_SOLID.create()));
            layout.add(new SideNavItem("Contacts", ContactView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

            layout.add(createSectionLabel("Medical Cases"));
            layout.add(new SideNavItem("Recent Cases", MedicalCaseView.class, LineAwesomeIcon.CROSS_SOLID.create()));
            layout.add(new SideNavItem("My Cases", MyMedicalCaseView.class, LineAwesomeIcon.BRAIN_SOLID.create()));
            layout.add(new SideNavItem("Create New Case", CreateNewMedicalCaseView.class, LineAwesomeIcon.BRIEFCASE_MEDICAL_SOLID.create()));

            layout.add(createSectionLabel("Messenger"));
            layout.add(new SideNavItem("Chats", MessengerView.class, LineAwesomeIcon.MAIL_BULK_SOLID.create()));

            // Add Admin Dashboard link conditionally
            if ("ADMIN".equals(currentUser.getRole())) {
                layout.add(createSectionLabel("Admin"));
                layout.add(new SideNavItem("Admin Dashboard", AdminDashboardView.class, LineAwesomeIcon.COG_SOLID.create()));
            }
        }

        return layout;
    }

    private Span createSectionLabel(String text) {
        Span label = new Span(text);
        label.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.Margin.SMALL);
        return label;
    }

    private Footer createFooter() {
        return new Footer();
    }

    private void addLogoutButton() {
        Button logoutButton = new Button("Logout", event -> showLogoutConfirmation());
        HorizontalLayout header = new HorizontalLayout(viewTitle, logoutButton);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setWidthFull();
        addToNavbar(header);
    }

    private void showLogoutConfirmation() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("Are you sure you want to log out?"));

        Button confirmButton = new Button("Logout", event -> {
            dialog.close();
            VaadinSession.getCurrent().getSession().invalidate();
            VaadinSession.getCurrent().close();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        Button cancelButton = new Button("Cancel", event -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
        dialog.add(buttons);
        dialog.open();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        User currentUser = VaadinSession.getCurrent().getAttribute(User.class);
        if (currentUser == null) {
            event.rerouteTo("login");
        }
    }
}