package com.fivepanels.application.views.home;

import com.fivepanels.application.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private H1 welcomeTitle;
    private H2 noticeMessage;
    private Div paragraph1;
    private Div paragraph2;
    private H2 subtitle1;
    private H2 subtitle2;
    private Div paragraph3;

    public HomeView() {
        initComponents();
        addComponents();
    }

    private void initComponents() {
        welcomeTitle = new H1("Welcome to FivePanels");
        noticeMessage = new H2("");

        subtitle1 = new H2("Why Our Application Stands Out");
        paragraph1 = new Div();
        paragraph1.setText("Our application revolutionizes the way users engage with medical cases. Designed with a user-friendly interface, it empowers users to create, manage, and interact with medical cases efficiently. Users can easily create detailed medical cases, complete with all necessary information and attachments, making it a vital tool for both medical professionals and enthusiasts. The streamlined process ensures that creating and managing cases is quick and straightforward, enhancing productivity and collaboration.");

        subtitle2 = new H2("Engage and Interact with Medical Cases");
        paragraph2 = new Div();
        paragraph2.setText("One of the standout features of our application is the ability for users to vote on other medical cases. This functionality fosters a collaborative environment where the best and most relevant cases rise to the top, driven by community engagement. Users can cast their votes, provide feedback, and contribute to discussions, ensuring that the platform remains dynamic and user-driven. This voting mechanism not only helps in highlighting the most critical cases but also promotes active participation from all users.");

        paragraph3 = new Div();
        paragraph3.setText("Our application goes beyond just managing medical cases; it enables users to build a network by adding people and chatting with them. Whether you need to consult a colleague, discuss a case in detail, or seek advice from experts, the integrated chat feature facilitates seamless communication. Users can add peers, create groups, and have real-time conversations, making collaboration effortless. This interconnected approach ensures that users can leverage the collective knowledge and expertise of the community, fostering a supportive and knowledgeable environment.");
    }

    private void addComponents() {
        setMargin(true);
        setSpacing(true);
        setPadding(true);
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        add(welcomeTitle, noticeMessage, subtitle1, paragraph1, subtitle2, paragraph2, paragraph3);
    }
}