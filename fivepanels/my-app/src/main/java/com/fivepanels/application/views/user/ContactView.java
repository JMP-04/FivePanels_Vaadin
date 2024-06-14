package com.fivepanels.application.views.user;

import com.fivepanels.application.model.domain.user.UserProfile;
import com.fivepanels.application.model.domain.user.misc.Hashtag;
import com.fivepanels.application.model.domain.user.misc.Language;
import com.fivepanels.application.model.domain.user.misc.MedicalTitle;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.File;
import java.util.List;
import java.util.Set;

@PageTitle("Contacts")
@Route(value = "user/contacts", layout = MainLayout.class)
public class ContactView extends VerticalLayout {

    private Grid<UserProfile> userGrid;

    public ContactView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        userGrid = new Grid<>(UserProfile.class, false);
        userGrid.addColumn(UserProfile::getFirstName).setHeader("First Name");
        userGrid.addColumn(UserProfile::getLastName).setHeader("Last Name");
        userGrid.addColumn(UserProfile::getCity).setHeader("City");
        userGrid.addColumn(userProfile ->
                userProfile.getExperiences().stream()
                        .map(Hashtag::getTag)
                        .reduce((tag1, tag2) -> tag1 + ", " + tag2)
                        .orElse("")
        ).setHeader("Hashtags");
        userGrid.addColumn(UserProfile::getActivityScore).setHeader("Activity Score");
        userGrid.addColumn(UserProfile::getExpertScore).setHeader("Expert Score");

        // Dummy data for demonstration
        userGrid.setItems(
                new UserProfile("John", "Doe", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("ACU"), new MedicalTitle("SURG")), List.of(new Hashtag("#Toxicology"), new Hashtag("#Dermatology"), new Hashtag("#Cardiology")), "New York", Set.of(new Language("German"))),
                new UserProfile("Jane", "Smith", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("PED"), new MedicalTitle("NEU")), List.of(new Hashtag("#Neurology"), new Hashtag("#Dermatology")), "Berlin", Set.of(new Language("English"))),
                new UserProfile("Emily", "Jones", new File("images/FivePanels-Logo.png"), List.of(new MedicalTitle("CAR"), new MedicalTitle("DER")), List.of(new Hashtag("#Cardiology")), "Paris", Set.of(new Language("French")))
        );
    }

    private void addComponents() {
        add(userGrid);
    }

    private void addListeners() {
        // Add any event listeners if needed
    }
}