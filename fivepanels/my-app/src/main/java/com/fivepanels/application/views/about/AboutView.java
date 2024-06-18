package com.fivepanels.application.views.about;

import com.fivepanels.application.security.roles.Roles;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("About")
@Route(value = "about/about", layout = MainLayout.class)
@RolesAllowed(Roles.USER)

public class AboutView extends VerticalLayout {

    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/FivePanels-Logo.png", "FivePanels Logo");
        img.setWidth("300px");
        add(img);

        H1 header = new H1("This is FivePanels, a software created for doctors to help each other around the world.");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);

        // Create Anchor for GitHub profile
        Anchor githubLink1 = new Anchor("https://github.com/JMP-04", "JMP-04");
        githubLink1.setTarget("_blank");

        Anchor githubLink2 = new Anchor("https://github.com/markoteric", "markoteric");
        githubLink2.setTarget("_blank");

        Span text1 = new Span("Made by ");
        text1.addClassName(FontSize.LARGE);

        Span text2 = new Span(" and ");
        text2.addClassName(FontSize.LARGE);

        Span text3 = new Span(".");
        text3.addClassName(FontSize.LARGE);

        Paragraph madeBy = new Paragraph(text1, githubLink1, text2, githubLink2, text3);
        madeBy.addClassName(FontSize.LARGE);
        add(madeBy);

        // Create Anchor for GitHub repository link
        Anchor githubRepoLink = new Anchor("https://github.com/JMP-04/FivePanels_Vaadin", "GitHub");
        githubRepoLink.setTarget("_blank");

        Span text4 = new Span("This software is free, open source and available on ");
        text4.addClassName(FontSize.LARGE);

        Span text5 = new Span(". Enjoy your stay! ☕");
        text5.addClassName(FontSize.LARGE);

        Paragraph softwareInfo = new Paragraph(text4, githubRepoLink, text5);
        softwareInfo.addClassName(FontSize.LARGE);
        add(softwareInfo);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}