package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.foundation.BaseEntity;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;

@PageTitle("My Cases")
@Route(value = "medical-cases/my-cases", layout = MainLayout.class)
public class MyMedicalCaseView extends VerticalLayout {

    private Grid<MedicalCase> grid;


    public MyMedicalCaseView() {

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        grid = new Grid<>();
        grid.addColumn(MedicalCase::getId).setHeader("ID");
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
