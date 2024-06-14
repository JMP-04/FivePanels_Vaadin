package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Create Medical Case | FivePanels")
@Route(value = "medical-cases/create", layout = MainLayout.class)
public class CreateNewMedicalCaseView extends VerticalLayout {

    private TextArea content;
    private Button createButton;


    public CreateNewMedicalCaseView() {

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        content = new TextArea();
        createButton = new Button("Publish case");
    }

    private void addComponents() {

        content.setMaxLength(1400);
        add(content, createButton);
    }

    private void addListeners() {

        createButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonOnClickEvent -> {
        });
    }
}
