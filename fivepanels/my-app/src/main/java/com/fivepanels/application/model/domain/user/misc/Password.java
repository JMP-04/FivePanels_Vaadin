package com.fivepanels.application.model.domain.user.misc;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.UserException;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.nulabinc.zxcvbn.Strength;

import java.util.Arrays;

public class Password {

    private String hashedPassword;

    public Password(char[] password) {
        setPassword(password);
    }

    public Password() {
        setPassword("StrongPassword123!FoobarLOL".toCharArray());
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setPassword(char[] passwordToHash) {
        Assertion.isNotNull(passwordToHash, "passwordToHash");
        String passwordStr = new String(passwordToHash);

        Assertion.isNotBlank(passwordStr, "passwordToHash");
        Assertion.hasMinLength(passwordStr, 8, "passwordToHash");
        Assertion.hasMaxLength(passwordStr, 128, "passwordToHash");
        Assertion.containsLetter(passwordStr, "passwordToHash");
        Assertion.containsNumber(passwordStr, "passwordToHash");
        Assertion.containsSpecialCharacter(passwordStr, "passwordToHash");

        if (!isPwStrongEnough(passwordStr)) {
            throw new UserException("Password is not strong enough");
        }

        this.hashedPassword = hashPassword(passwordToHash);
    }

    private String hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToString(12, password);
    }

    public boolean matches(char[] rawPassword) {
        return BCrypt.verifyer().verify(rawPassword, this.hashedPassword.toCharArray()).verified;
    }

    private boolean isPwStrongEnough(CharSequence password) {
        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(password);
        return strength.getScore() >= 3;
    }

    @Override
    public String toString() {
        return hashedPassword;
    }
}
