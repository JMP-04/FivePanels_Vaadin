package com.fivepanels.application.views.medicalcase;

import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.model.domain.user.misc.Hashtag;
import com.fivepanels.application.model.repository.MedicalCaseRepository;
import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Create New Case")
@Route(value = "medical-cases/create-new-case", layout = MainLayout.class)
@Component
public class CreateNewMedicalCaseView extends VerticalLayout {

    private TextArea medicalCaseName;
    private TextArea title;
    private TextArea description;
    private TextArea content;
    private MultiSelectComboBox<Hashtag> hashtagComboBox;
    private Button createButton;
    private Upload upload;
    private Div uploadInfo;
    private List<File> files;
    private MedicalCase medicalCase;

    private MedicalCaseRepository medicalCaseRepository;

    public CreateNewMedicalCaseView() {
        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        medicalCaseName = new TextArea();
        title = new TextArea();
        description = new TextArea();
        content = new TextArea();
        createButton = new Button("Publish");

        MultiFileBuffer buffer = new MultiFileBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", ".jpg", "image/png", ".png");
        upload.setMaxFiles(10);
        upload.setDropLabelIcon(VaadinIcon.FILE.create());
        upload.setMaxFileSize(3840 * 2160);

        uploadInfo = new Div();
        uploadInfo.setText("Accepted file types: .jpg, .png | Max file count: 10 | Max resolution: 3840 x 2160");

        files = new ArrayList<>();

        hashtagComboBox = new MultiSelectComboBox<>();
        hashtagComboBox.setWidth("25%");
        hashtagComboBox.setLabel("Select Hashtags");
        hashtagComboBox.setPlaceholder("Select or add hashtags");
        hashtagComboBox.setItems(loadHashtags());
        hashtagComboBox.setItemLabelGenerator(Hashtag::getTag);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private List<Hashtag> loadHashtags() {
        return List.of(
                new Hashtag("#AcademicMedicine"),
                new Hashtag("#AdhesiveDentistry"),
                new Hashtag("#AbdominalSurgery"),
                new Hashtag("#AdolescentMedicine"),
                new Hashtag("#AddictionMedicine"),
                new Hashtag("#Agada"),
                new Hashtag("#Alcohology"),
                new Hashtag("#Algesiology"),
                new Hashtag("#Allergology"),
                new Hashtag("#Anaesthesiology"),
                new Hashtag("#Anaplastology"),
                new Hashtag("#AnatomicalPathology"),
                new Hashtag("#Andrology"),
                new Hashtag("#Angiology"),
                new Hashtag("#AssessmentMedicine"),
                new Hashtag("#AestheticMedicine"),
                new Hashtag("#AviationMedicine"),
                new Hashtag("#Balneology"),
                new Hashtag("#BattlefieldMedicine"),
                new Hashtag("#BehavioralMedicine"),
                new Hashtag("#BoneSurgery"),
                new Hashtag("#Bronchology"),
                new Hashtag("#BurnSurgery"),
                new Hashtag("#BariatricMedicine"),
                new Hashtag("#CardiacSurgery"),
                new Hashtag("#Cardiology"),
                new Hashtag("#Cardiooncology"),
                new Hashtag("#Cardiogeriatrics"),
                new Hashtag("#Cardiogenetics"),
                new Hashtag("#ChildPulmonology"),
                new Hashtag("#ClinicalEpidemiology"),
                new Hashtag("#ClinicalGenetics"),
                new Hashtag("#ClinicalImmunology"),
                new Hashtag("#ClinicalMedicine"),
                new Hashtag("#ClinicalNeurophysiology"),
                new Hashtag("#ClinicalNutrition"),
                new Hashtag("#ClinicalOncology"),
                new Hashtag("#ClinicalPathology"),
                new Hashtag("#ClinicalPharmacology"),
                new Hashtag("#ClinicalPsychology"),
                new Hashtag("#ClinicalVirology"),
                new Hashtag("#Colpocleisis"),
                new Hashtag("#Coloproctology"),
                new Hashtag("#ColorectalSurgery"),
                new Hashtag("#ComplementaryMedicine"),
                new Hashtag("#CommunityMedicine"),
                new Hashtag("#CraniofacialSurgery"),
                new Hashtag("#CriticalEmergencyMedicine"),
                new Hashtag("#DentalMedicine"),
                new Hashtag("#Dermatology"),
                new Hashtag("#Dermatopathology"),
                new Hashtag("#Dermatovenerology"),
                new Hashtag("#Desmurgy"),
                new Hashtag("#Dietetics"),
                new Hashtag("#DiagnosticMicrobiology"),
                new Hashtag("#DiagnosticNeuroradiology"),
                new Hashtag("#DivingMedicine"),
                new Hashtag("#EarSurgery"),
                new Hashtag("#EmergencyMedicine"),
                new Hashtag("#EmergencyNursing"),
                new Hashtag("#Emetology"),
                new Hashtag("#EndocrineOncology"),
                new Hashtag("#Endocrinology"),
                new Hashtag("#Endodontics"),
                new Hashtag("#Endoscopy"),
                new Hashtag("#EnvironmentalMedicine"),
                new Hashtag("#EquineMedicine"),
                new Hashtag("#Essure"),
                new Hashtag("#FamilyMedicine"),
                new Hashtag("#FamilyPlanning"),
                new Hashtag("#FamilyPlanningMethods"),
                new Hashtag("#FetalSurgery"),
                new Hashtag("#ForensicMedicine"),
                new Hashtag("#FootAndAnkleSurgery"),
                new Hashtag("#FunctionalSurgery"),
                new Hashtag("#Gastroenterology"),
                new Hashtag("#GastrointestinalSurgery"),
                new Hashtag("#GeneralPractice"),
                new Hashtag("#GeneralSurgery"),
                new Hashtag("#GeneticCounseling"),
                new Hashtag("#GenitourinaryMedicine"),
                new Hashtag("#GenitourinaryOncology"),
                new Hashtag("#Geriatrics"),
                new Hashtag("#GeriatricDentistry"),
                new Hashtag("#GeriatricMedicine"),
                new Hashtag("#GeriatricPsychiatry"),
                new Hashtag("#GeriatricTraumatology"),
                new Hashtag("#Gerontopsychiatry"),
                new Hashtag("#GynecologicOncology"),
                new Hashtag("#GynecologicalSurgery"),
                new Hashtag("#Gynaecology"),
                new Hashtag("#HandSurgery"),
                new Hashtag("#HaemostasisSpecialty"),
                new Hashtag("#HeadAndNeckSurgery"),
                new Hashtag("#Hematology"),
                new Hashtag("#Hematopathology"),
                new Hashtag("#Hepatology"),
                new Hashtag("#HospitalMedicine"),
                new Hashtag("#HumanDentalMedicine"),
                new Hashtag("#ImmunoOncology"),
                new Hashtag("#Immunopathology"),
                new Hashtag("#Immunotoxicology"),
                new Hashtag("#InfectionEpidemiology"),
                new Hashtag("#InfectiousDiseases"),
                new Hashtag("#InsuranceMedicine"),
                new Hashtag("#IntegrativeMedicine"),
                new Hashtag("#IntensiveCareMedicine"),
                new Hashtag("#InterventionalCardiology"),
                new Hashtag("#InterventionalNeuroradiology"),
                new Hashtag("#InterventionalPainManagement"),
                new Hashtag("#InterventionalRadiology"),
                new Hashtag("#LiverSurgery"),
                new Hashtag("#Laryngology"),
                new Hashtag("#LifestyleMedicine"),
                new Hashtag("#Lipidology"),
                new Hashtag("#Lymphology"),
                new Hashtag("#Mammology"),
                new Hashtag("#MaritimeMedicine"),
                new Hashtag("#MassGatheringMedicine"),
                new Hashtag("#MaternalFetalMedicine"),
                new Hashtag("#Mastology"),
                new Hashtag("#MaxillofacialSurgery"),
                new Hashtag("#MedicalEntomology"),
                new Hashtag("#MedicalGenetics"),
                new Hashtag("#MedicalJurisprudence"),
                new Hashtag("#MedicalMicrobiology"),
                new Hashtag("#MedicalOncology"),
                new Hashtag("#MedicalParasitology"),
                new Hashtag("#MedicalRehabilitation"),
                new Hashtag("#MedicalToxicology"),
                new Hashtag("#MinimallyInvasiveStrabismusSurgery"),
                new Hashtag("#MilitaryMedicine"),
                new Hashtag("#MolecularImmunology"),
                new Hashtag("#MolecularMedicine"),
                new Hashtag("#MolecularOncology"),
                new Hashtag("#MolecularVirology"),
                new Hashtag("#Neonatology"),
                new Hashtag("#Nephrology"),
                new Hashtag("#Neuroendocrinology"),
                new Hashtag("#Neuroembryology"),
                new Hashtag("#Neuroimmunology"),
                new Hashtag("#NeurointensiveCare"),
                new Hashtag("#Neurology"),
                new Hashtag("#NeuromuscularMedicine"),
                new Hashtag("#Neuropathology"),
                new Hashtag("#Neuropsychiatry"),
                new Hashtag("#Neurotology"),
                new Hashtag("#Neuroradiology"),
                new Hashtag("#Neuropediatrics"),
                new Hashtag("#Neurosurgery"),
                new Hashtag("#Nosology"),
                new Hashtag("#NuclearMedicine"),
                new Hashtag("#NutritionScience"),
                new Hashtag("#ObstetricMedicine"),
                new Hashtag("#Obstetrics"),
                new Hashtag("#ObstetricsAndGynaecology"),
                new Hashtag("#OccupationalHealth"),
                new Hashtag("#OccupationalMedicine"),
                new Hashtag("#Oncogenetics"),
                new Hashtag("#Oncogenomics"),
                new Hashtag("#OncologicalPlasticSurgery"),
                new Hashtag("#Oncology"),
                new Hashtag("#OphthalmicPathology"),
                new Hashtag("#Ophthalmology"),
                new Hashtag("#OralAndMaxillofacialRadiology"),
                new Hashtag("#OralAndMaxillofacialSurgery"),
                new Hashtag("#OralMedicine"),
                new Hashtag("#OralPathology"),
                new Hashtag("#OralSurgery"),
                new Hashtag("#OperationalMedicine"),
                new Hashtag("#OrthognathicSurgery"),
                new Hashtag("#Orthopedics"),
                new Hashtag("#OrthopedicSurgery"),
                new Hashtag("#Orthotics"),
                new Hashtag("#Otolaryngology"),
                new Hashtag("#Otology"),
                new Hashtag("#PainManagement"),
                new Hashtag("#PainMedicine"),
                new Hashtag("#PalliativeMedicine"),
                new Hashtag("#Pathology"),
                new Hashtag("#Pathophysiology"),
                new Hashtag("#Pedaudiology"),
                new Hashtag("#PediatricCardiacSurgery"),
                new Hashtag("#PediatricDentistry"),
                new Hashtag("#PediatricEndocrinology"),
                new Hashtag("#PediatricGastroenterology"),
                new Hashtag("#PediatricGynaecology"),
                new Hashtag("#PediatricNephrology"),
                new Hashtag("#PediatricNeurology"),
                new Hashtag("#PediatricNeurosurgery"),
                new Hashtag("#PediatricOncology"),
                new Hashtag("#PediatricOphthalmology"),
                new Hashtag("#PediatricOrthopedicSurgery"),
                new Hashtag("#PediatricPathology"),
                new Hashtag("#PediatricPlasticSurgery"),
                new Hashtag("#PediatricRadiology"),
                new Hashtag("#PediatricSurgery"),
                new Hashtag("#PediatricTraumatology"),
                new Hashtag("#PediatricUrology"),
                new Hashtag("#Pediatrics"),
                new Hashtag("#PerinatalPsychiatry"),
                new Hashtag("#PharmaceuticalMedicine"),
                new Hashtag("#Pharmacology"),
                new Hashtag("#Phlebology"),
                new Hashtag("#Phoniatrics"),
                new Hashtag("#PhysicalMedicineAndRehabilitation"),
                new Hashtag("#Physiology"),
                new Hashtag("#Phthisiology"),
                new Hashtag("#PlasticSurgery"),
                new Hashtag("#Podiatry"),
                new Hashtag("#PredictiveMedicine"),
                new Hashtag("#PreHospitalEmergencyMedicine"),
                new Hashtag("#PreventiveMedicine"),
                new Hashtag("#PrisonHealthcare"),
                new Hashtag("#Psychiatry"),
                new Hashtag("#PsychiatricSemiology"),
                new Hashtag("#PsychosomaticMedicine"),
                new Hashtag("#PublicHealthPhysician"),
                new Hashtag("#PublicMedicine"),
                new Hashtag("#RadiationOncology"),
                new Hashtag("#RadiationTherapy"),
                new Hashtag("#Radiology"),
                new Hashtag("#ReconstructiveSurgery"),
                new Hashtag("#RefractiveSurgery"),
                new Hashtag("#RegenerativeMedicine"),
                new Hashtag("#RenalPathology"),
                new Hashtag("#ReproductiveEndocrinologyAndInfertility"),
                new Hashtag("#ReproductiveMedicine"),
                new Hashtag("#Rhinology"),
                new Hashtag("#Rhythmology"),
                new Hashtag("#Roentgenology"),
                new Hashtag("#ScienceOfProsthetics"),
                new Hashtag("#SexualMedicine"),
                new Hashtag("#SleepMedicine"),
                new Hashtag("#SocialMedicine"),
                new Hashtag("#SocialPsychiatry"),
                new Hashtag("#SpeechLanguagePathology"),
                new Hashtag("#SpineSurgery"),
                new Hashtag("#SportsChiropractic"),
                new Hashtag("#SportsMedicine"),
                new Hashtag("#SportsPsychiatry"),
                new Hashtag("#SportsTraumatology"),
                new Hashtag("#Strabology"),
                new Hashtag("#StrabismusSurgery"),
                new Hashtag("#SurgicalEndocrinology"),
                new Hashtag("#SurgicalOncology"),
                new Hashtag("#Symptomatology"),
                new Hashtag("#TauroTraumatology"),
                new Hashtag("#TechnicalMedicine"),
                new Hashtag("#ThroatSurgery"),
                new Hashtag("#ThoracicOncology"),
                new Hashtag("#ThoracicSurgery"),
                new Hashtag("#Toxicology"),
                new Hashtag("#TraumaSurgery"),
                new Hashtag("#Traumatology"),
                new Hashtag("#TravelMedicine"),
                new Hashtag("#Urogynecology"),
                new Hashtag("#Urology"),
                new Hashtag("#VeterinaryMedicine"),
                new Hashtag("#VeterinarySurgery"),
                new Hashtag("#Vertebroneurology"),
                new Hashtag("#VascularSurgery"),
                new Hashtag("#Venereology")
        );
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

        description.setMinLength(4);
        description.setMaxLength(256);
        description.setWidth("80%");
        description.setLabel("Please enter a short description of the new medical case.");
        description.setPlaceholder("Enter your description here.");

        content.setMinLength(128);
        content.setMaxLength(2048);
        content.setWidth("80%");
        content.setLabel("Please enter the content of the new medical case. The medical case will be created with the content you entered and the files you uploaded.");
        content.setPlaceholder("Enter your content here.");

        add(medicalCaseName, title, description, content, hashtagComboBox, uploadInfo, upload, createButton);
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
                if (medicalCaseName.getValue() == null || medicalCaseName.getValue().isEmpty()) {
                    Notification notification = Notification.show("Please enter a value for the name of the new medical case.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                if (title.getValue() == null || title.getValue().isEmpty()) {
                    Notification notification = Notification.show("Please enter a value for the title of the new medical case.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                if (description.getValue() == null || description.getValue().isEmpty()) {
                    Notification notification = Notification.show("Please enter a value for the description of the new medical case.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

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
                medicalCase.setMedicalCaseName(medicalCaseName.getValue());
                medicalCase.setTitle(title.getValue());
                medicalCase.setDescription(description.getValue());
                medicalCase.setTextContent(content.getValue());
                medicalCase.setFileContent(files);
                medicalCase.setMedicalCaseHashtags(hashtagComboBox.getSelectedItems());
                MedicalCaseRepository.save(medicalCase);

                getUI().ifPresent(ui -> ui.navigate(MyMedicalCaseView.class));
            } catch (Exception e) {
                Notification notification = Notification.show("Error occurred, please try again later. :(");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}
