package com.fivepanels.application.views;

import com.fivepanels.application.views.about.AboutView;
import com.fivepanels.application.views.home.HomeView;
import com.fivepanels.application.views.medicalcase.CreateNewMedicalCaseView;
import com.fivepanels.application.views.medicalcase.MedicalCaseView;
import com.fivepanels.application.views.medicalcase.MyMedicalCaseView;
import com.fivepanels.application.views.messenger.MessengerView;
import com.fivepanels.application.views.user.ContactView;
import com.fivepanels.application.views.user.FriendRequestView;
import com.fivepanels.application.views.user.LoginView;
import com.fivepanels.application.views.user.ProfileView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {

        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {

        Span appName = new Span("FivePanels");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE, LumoUtility.TextColor.PRIMARY);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private VerticalLayout createNavigation() {

        VerticalLayout layout = new VerticalLayout();

        layout.add(createSectionLabel("Home"));
        layout.add(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

        layout.add(createSectionLabel("About"));
        layout.add(new SideNavItem("About", AboutView.class, LineAwesomeIcon.INFO_SOLID.create()));

        layout.add(createSectionLabel("User"));
        layout.add(new SideNavItem("Login", LoginView.class, LineAwesomeIcon.BRAIN_SOLID.create()));
        layout.add(new SideNavItem("Profile", ProfileView.class, LineAwesomeIcon.USER_SOLID.create()));
        layout.add(new SideNavItem("Friend Requests", FriendRequestView.class, LineAwesomeIcon.PEOPLE_CARRY_SOLID.create()));
        layout.add(new SideNavItem("Contacts", ContactView.class, LineAwesomeIcon.GLOBE_SOLID.create()));


        layout.add(createSectionLabel("Medical Cases"));
        layout.add(new SideNavItem("Recent Cases", MedicalCaseView.class, LineAwesomeIcon.CROSS_SOLID.create()));
        layout.add(new SideNavItem("My Cases", MyMedicalCaseView.class, LineAwesomeIcon.BRAIN_SOLID.create()));
        layout.add(new SideNavItem("Create New Case", CreateNewMedicalCaseView.class, LineAwesomeIcon.BRIEFCASE_MEDICAL_SOLID.create()));

        layout.add(createSectionLabel("Messenger"));
        layout.add(new SideNavItem("Chats", MessengerView.class, LineAwesomeIcon.MAIL_BULK_SOLID.create()));

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

    @Override
    protected void afterNavigation() {

        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {

        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
