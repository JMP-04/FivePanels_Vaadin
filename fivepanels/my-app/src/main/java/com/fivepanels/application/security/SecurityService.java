package com.fivepanels.application.security;

import com.fivepanels.application.model.domain.user.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public class SecurityService {

    public static boolean isAuthenticated() {
        return VaadinSession.getCurrent().getAttribute(User.class) != null;
    }

    public static void checkAccess() {
        if (!isAuthenticated()) {
            UI.getCurrent().navigate("login");
        }
    }
}