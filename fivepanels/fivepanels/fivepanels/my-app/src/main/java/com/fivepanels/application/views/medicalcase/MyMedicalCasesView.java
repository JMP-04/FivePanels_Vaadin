package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.service.MedicalCase;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Set;

@PageTitle("Create Medical Case | FivePanels")
@Route(value = "medical-cases/my-cases", layout = MainLayout.class)
public class MyMedicalCasesView extends VerticalLayout {

    private Set<MedicalCase> myMedicalCases;
    private Grid<MedicalCase> grid;

    public MyMedicalCasesView() {

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        grid.addColumn(MedicalCase::getID).setHeader("ID");
        grid.addColumn(MedicalCase::getTitle).setHeader("Title");
        grid.addColumn(MedicalCase::getStatus).setHeader("Status");
        grid.addColumn(MedicalCase::getCreated).setHeader("Created");
        grid.addColumn(MedicalCase::getUpdated).setHeader("Modified");
        grid.addColumn(MedicalCase::getMemberCount).setHeader("Member Count");
    }

    private void addComponents() {


        add(grid);
    }

    private void addListeners() {
    }
}
