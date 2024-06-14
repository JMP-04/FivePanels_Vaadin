package com.fivepanels.application.model.domain.medicalcase;

import com.fivepanels.application.model.domain.user.User;
import com.fivepanels.application.model.foundation.assertion.Assertion;

public class Vote {

    private User user;
    private Answer answer;
    private Integer percentage;

    public Vote(User user, Answer answer, Integer percentage) {
        this.user = user;
        this.answer = answer;
        setPercentage(percentage);
    }

    public User getUser() {
        return user;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        Assertion.isTrue((percentage == 100), () -> "Percentage must be exactly 100");
        this.percentage = percentage;
    }
}
