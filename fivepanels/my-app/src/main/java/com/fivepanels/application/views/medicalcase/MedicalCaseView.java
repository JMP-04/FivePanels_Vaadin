package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        grid.addColumn(MedicalCase::getId).setHeader("ID");
        grid.addColumn(MedicalCase::getOwner).setHeader("Owner");
        grid.addColumn(MedicalCase::getDescription).setHeader("Description");
        grid.addColumn(MedicalCase::getViewCount).setHeader("View Count");
        grid.addColumn(MedicalCase::getLikeCount).setHeader("Like Count");
        grid.addColumn(MedicalCase::getCreatedAt).setHeader("Created At");
        grid.addColumn(MedicalCase::getUpdatedAt).setHeader("Updated At");
        grid.addColumn(MedicalCase::getMedicalCaseHashtags).setHeader("Hashtags");
        add(grid);
    }

    private void addComponents() {


    }

    private void addListeners() {


    }
}
