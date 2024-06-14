package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Medical Cases | FivePanels")
@Route(value = "medical-cases", layout = MainLayout.class)

public class MedicalCaseView extends VerticalLayout {

    public MedicalCaseView() {

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

    }

    private void addComponents() {


    }

    private void addListeners() {


    }
}
