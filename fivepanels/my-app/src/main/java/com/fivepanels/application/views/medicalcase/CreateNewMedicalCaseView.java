package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Div;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Create Medical Case | FivePanels")
@Route(value = "medical-cases/create", layout = MainLayout.class)
public class CreateNewMedicalCaseView extends VerticalLayout {

    private TextArea content;
    private Button createButton;
    private Upload upload;
    private Div uploadInfo;
    private List<File> files;
    private MedicalCase medicalCase;

    public CreateNewMedicalCaseView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {

        content = new TextArea();
        createButton = new Button("Create");

        MultiFileBuffer buffer = new MultiFileBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", ".jpg", "image/png", ".png");
        upload.setMaxFiles(10);
        upload.setDropLabelIcon(VaadinIcon.FILE.create());
        upload.setMaxFileSize(3840 * 2160);

        uploadInfo = new Div();
        uploadInfo.setText("Accepted file types: .jpg, .png | Max file count: 10 |  Max resolution: 3840 x 2160");

        files = new ArrayList<>();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void addComponents() {

        content.setMinLength(128);
        content.setMaxLength(2048);
        content.setWidth("80%");
        content.setLabel("Please enter the content of the new medical case. The medical case will be created with the content you entered and the files you uploaded.");
        content.setValue("Enter your content here.");

        add(content, uploadInfo, upload, createButton);
    }

    private void addListeners() {
        content.addValueChangeListener(e -> {
            e.getSource().setHelperText(e.getValue().length() + "/" + content.getMaxLength());
        });

        upload.addSucceededListener((ComponentEventListener<SucceededEvent>) event -> {
            MultiFileBuffer buffer = (MultiFileBuffer) upload.getReceiver();
            files.add(buffer.getFileData(event.getFileName()).getFile());
        });

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            Notification notification = Notification.show(
                    errorMessage,
                    5000,
                    Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        createButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonOnClickEvent -> {
            try {
                if (content.getValue() == null || content.getValue().isEmpty()) {
                    Notification.show("Please enter a value for the content of the new medical case.");
                    return;
                }

                if (content.getValue().length() < 128) {
                    Notification.show("The content of the new medical case is too short. Your content character-length: " + content.getValue().length() + " , required: " + content.getMinLength());
                    return;
                }

                if (content.getValue().length() > 2048) {
                    Notification.show("The content of the new medical case is too long. Your content character-length: " + content.getValue().length() + " , length cannot exceed: " + content.getMaxLength());
                    return;
                }

                medicalCase = new MedicalCase();
                // Handle the logic for saving the medical case and the uploaded files.
                getUI().ifPresent(ui -> ui.navigate(MedicalCaseView.class));
            } catch (Exception e) {
                Notification.show("Error occurred, please try again later. :(");
            }
        });
    }
}