package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.model.repository.MedicalCaseRepository;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("My Cases")
@Route(value = "medical-cases/my-cases", layout = MainLayout.class)

public class MyMedicalCaseView extends VerticalLayout {

    private Grid<MedicalCase> grid;

    public MyMedicalCaseView() {
        initComponents();
        addComponents();
        addListeners();
        loadMedicalCases();
    }

    private void initComponents() {
        grid = new Grid<>();

        grid.addColumn(new ComponentRenderer<>(medicalCase -> {
            VerticalLayout layout = new VerticalLayout();

            H1 id = new H1(medicalCase.getId().toString());
            id.getStyle().set("font-weight", "bold");
            layout.add(id);
            layout.add(new Span("Name: " + medicalCase.getMedicalCaseName()));
            layout.add(new Span("Title: " + medicalCase.getTitle()));
            layout.add(new Span("Description: " + medicalCase.getDescription()));
            layout.add(new Span("Content: " + medicalCase.getTextContent()));
            layout.add(new Span("View Count: " + medicalCase.getViewCount()));
            layout.add(new Span("Like Count: " + medicalCase.getLikeCount()));
            layout.add(new Span("Created At: " + medicalCase.getCreatedAt()));
            layout.add(new Span("Hashtags: " + medicalCase.getMedicalCaseHashtags().toString()));

            return layout;
        })).setHeader("My Medical Cases");
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
            Notification.show("Error loading medical cases.", 3000, Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}