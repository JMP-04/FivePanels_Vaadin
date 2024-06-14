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

@PageTitle("Create New Case")
@Route(value = "medical-cases/create-new-case", layout = MainLayout.class)
public class CreateNewMedicalCaseView extends VerticalLayout {

    private TextArea medicalCaseName;

    private TextArea title;
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

        medicalCaseName = new TextArea();
        title = new TextArea();

        content = new TextArea();
        createButton = new Button("Publish");

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

        medicalCaseName.setMinLength(4);
        medicalCaseName.setMaxLength(64);
        medicalCaseName.setWidth("25%");
        medicalCaseName.setLabel("Name of your medical case.");
        medicalCaseName.setPlaceholder("Enter your medical case name here.");

        title.setMinLength(4);
        title.setMaxLength(64);
        title.setWidth("25%");
        title.setLabel("Title of your medical case.");
        title.setPlaceholder("Enter your medical case title here.");

        content.setMinLength(128);
        content.setMaxLength(2048);
        content.setWidth("80%");
        content.setLabel("Please enter the content of the new medical case. The medical case will be created with the content you entered and the files you uploaded.");
        content.setPlaceholder("Enter your content here.");

        add(medicalCaseName, title,content, uploadInfo, upload, createButton);
    }

    private void addListeners() {

        upload.addSucceededListener((ComponentEventListener<SucceededEvent>) event -> {
            MultiFileBuffer buffer = (MultiFileBuffer) upload.getReceiver();
            files.add(buffer.getFileData(event.getFileName()).getFile());
            Notification notification = Notification.show("File uploaded successfully.");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
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
                    Notification notification = Notification.show("Please enter a value for the content of the new medical case.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                if (content.getValue().length() < 128) {
                    Notification notification = Notification.show("The content of the new medical case is too short. Your content character-length: " + content.getValue().length() + " , required: " + content.getMinLength());
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                if (content.getValue().length() > 2048) {
                    Notification notification = Notification.show("The content of the new medical case is too long. Your content character-length: " + content.getValue().length() + " , length cannot exceed: " + content.getMaxLength());
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                medicalCase = new MedicalCase();
                // Handle the logic for saving the medical case and the uploaded files.
                getUI().ifPresent(ui -> ui.navigate(MedicalCaseView.class));
            } catch (Exception e) {
                Notification notification = Notification.show("Error occurred, please try again later. :(");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}