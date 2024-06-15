package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.model.repository.MedicalCaseRepository;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.logging.Level;
import java.util.logging.Logger;

@PageTitle("My Cases")
@Route(value = "medical-cases/my-cases", layout = MainLayout.class)
public class MyMedicalCaseView extends VerticalLayout {

    private Grid<MedicalCase> grid;
    private static final Logger LOGGER = Logger.getLogger(MyMedicalCaseView.class.getName());

    public MyMedicalCaseView() {
        initComponents();
        addComponents();
        addListeners();
        loadMedicalCases();
    }

    private void initComponents() {
        grid = new Grid<>();
        grid.addColumn(MedicalCase::getId).setHeader("ID");
        grid.addColumn(MedicalCase::getMedicalCaseName).setHeader("Name");
        grid.addColumn(MedicalCase::getTitle).setHeader("Title");
        grid.addColumn(MedicalCase::getDescription).setHeader("Description");
        grid.addColumn(MedicalCase::getViewCount).setHeader("View Count");
        grid.addColumn(MedicalCase::getLikeCount).setHeader("Like Count");
        grid.addColumn(MedicalCase::getCreatedAt).setHeader("Created At");
        grid.addColumn(MedicalCase::getMedicalCaseHashtags).setHeader("Hashtags");
    }

    private void addComponents() {
        add(grid);
    }

    private void addListeners() {
    }

    private void loadMedicalCases() {
        try {
            grid.setItems(MedicalCaseRepository.findAll());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading medical cases", e);
            Notification.show("Error loading medical cases.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}