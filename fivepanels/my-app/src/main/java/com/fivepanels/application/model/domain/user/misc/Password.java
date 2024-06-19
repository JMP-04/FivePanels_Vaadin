package com.fivepanels.application.model.domain.user.misc;

import java.util.Arrays;

public class Password {

    private char[] password;

    public Password(char[] password) {
        this.password = Arrays.copyOf(password, password.length);
    }

    public char[] getPassword() {
        return Arrays.copyOf(password, password.length);
    }

    public boolean matches(char[] inputPassword) {
        return Arrays.equals(this.password, inputPassword);
    }
}