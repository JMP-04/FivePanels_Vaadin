package com.fivepanels.application.views.admin;

import com.fivepanels.application.views.MainLayout;
import com.fivepanels.application.views.home.HomeView;
import com.fivepanels.application.views.user.AdminDashboardView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

@Route(value = "", layout = AdminLayout.class)
public class AdminLayout extends AppLayout {

    private H1 viewTitle;

    public AdminLayout() {
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
        Span appName = new Span("Admin Panel");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE, LumoUtility.TextColor.PRIMARY);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private VerticalLayout createNavigation() {
        VerticalLayout layout = new VerticalLayout();

        layout.add(new SideNavItem("Home", HomeView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        layout.add(new SideNavItem("Admin Dashboard", AdminDashboardView.class, LineAwesomeIcon.COG_SOLID.create()));

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
