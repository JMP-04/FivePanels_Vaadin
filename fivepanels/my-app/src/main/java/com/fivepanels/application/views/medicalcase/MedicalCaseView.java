package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Recent Cases")
@Route(value = "medical-cases/recent-cases", layout = MainLayout.class)
public class MedicalCaseView extends VerticalLayout {

    private Grid<MedicalCase> grid;

    public MedicalCaseView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        grid = new Grid<>();

        grid.addColumn(new ComponentRenderer<>(medicalCase -> {
            VerticalLayout layout = new VerticalLayout();

            H1 id = new H1(medicalCase.getId().toString());
            id.getStyle().set("font-weight", "bold");
            layout.add(id);

            layout.add(new Span("Owner: " + medicalCase.getOwner()));
            layout.add(new Span("Description: " + medicalCase.getDescription()));
            layout.add(new Span("View Count: " + medicalCase.getViewCount()));
            layout.add(new Span("Like Count: " + medicalCase.getLikeCount()));
            layout.add(new Span("Created At: " + medicalCase.getCreatedAt()));
            layout.add(new Span("Updated At: " + medicalCase.getUpdatedAt()));
            layout.add(new Span("Hashtags: " + medicalCase.getMedicalCaseHashtags().toString()));

            return layout;
        })).setHeader("Medical Cases");
    }

    private void addComponents() {
        add(grid);
    }

    private void addListeners() {
        // Add any event listeners if needed
    }
}