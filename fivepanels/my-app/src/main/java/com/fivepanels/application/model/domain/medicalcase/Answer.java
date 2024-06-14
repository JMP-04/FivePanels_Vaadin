package com.fivepanels.application.model.domain.medicalcase;

import com.fivepanels.application.model.foundation.BaseEntity;

public class Answer extends BaseEntity {
    private String answerText;
    private boolean isCorrect;

    public Answer(String answerText, boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}