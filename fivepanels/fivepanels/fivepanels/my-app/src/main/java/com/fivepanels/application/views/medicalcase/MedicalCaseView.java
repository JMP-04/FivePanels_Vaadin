package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.service.MedicalCase;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Medical Cases | FivePanels")
@Route(value = "medical-cases", layout = MainLayout.class)

public class MedicalCaseView extends VerticalLayout {

    private Grid<MedicalCase> grid;
    private Button createMedicalCase;
    private Button myMedicalCases;

    public MedicalCaseView() {

        initComponents();
        addComponents();
        addListeners();
    }


    private void initComponents() {

        grid = new Grid<>(MedicalCase.class, false);
        createMedicalCase = new Button("Create new medical case");
        add(grid, createMedicalCase);
    }

    private void addComponents() {

        grid.addColumn(MedicalCase::getID).setHeader("ID");
        grid.addColumn(MedicalCase::getOwner).setHeader("Owner");
        grid.addColumn(MedicalCase::getTitle).setHeader("Title");
        grid.addColumn(MedicalCase::getStatus).setHeader("Status");
        grid.addColumn(MedicalCase::getCreated).setHeader("Created");
        grid.addColumn(MedicalCase::getUpdated).setHeader("Modified");
        grid.addColumn(MedicalCase::getMemberCount).setHeader("Member Count");
    }

    private void addListeners() {

        createMedicalCase.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("medical-cases/create")));
        myMedicalCases.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("medical-cases/my-cases")));
    }
}
