package com.fivepanels.application.model.domain.user;

import com.fivepanels.application.model.domain.user.misc.Hashtag;
import com.fivepanels.application.model.domain.user.misc.Language;
import com.fivepanels.application.model.domain.user.misc.MedicalTitle;
import com.fivepanels.application.model.foundation.assertion.Assertion;

import java.io.File;
import java.util.*;

public class UserProfile {

    private String firstName;
    private String lastName;
    private File profilePicture;
    private List<MedicalTitle> medicalTitles;
    private List<Hashtag> experiences;
    private String city;
    private Set<Language> languages;
    private Integer activityScore;
    private Integer expertScore;
    private List<Integer> correctAnswerPercentages;

    public UserProfile() {
        this.firstName = null;
        this.lastName = null;
        this.profilePicture = getDefaultProfilePicture();
        this.medicalTitles = null;
        this.experiences = null;
        this.city = null;
        this.languages = null;
        this.activityScore = 0;
        this.expertScore = 0;
        this.correctAnswerPercentages = new ArrayList<>();
    }

    public UserProfile(String firstName, String lastName, File profilePicture, List<MedicalTitle> medicalTitles, List<Hashtag> experiences, String city, Set<Language> languages) {
        setFirstName(firstName);
        setLastName(lastName);
        setProfilePicture(profilePicture != null ? profilePicture : getDefaultProfilePicture());
        setMedicalTitles(medicalTitles);
        setExperiences(experiences);
        setCity(city);
        setLanguages(languages);
        setActivityScore(0);
        setExpertScore(0);
        this.correctAnswerPercentages = new ArrayList<>();
    }

    public UserProfile(String firstName, String lastName, String city) {
        setFirstName(firstName);
        setLastName(lastName);
        this.medicalTitles = new ArrayList<>();
        this.experiences = new ArrayList<>();
        setCity(city);
        this.languages = new HashSet<>();
        setActivityScore(0);
        setExpertScore(0);
        this.correctAnswerPercentages = new ArrayList<>();
    }

    // Getters and setters...

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Assertion.isNotNull(firstName, "firstName");
        Assertion.isNotBlank(firstName, "firstName");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        Assertion.isNotNull(lastName, "lastName");
        Assertion.isNotBlank(lastName, "lastName");
        this.lastName = lastName;
    }

    public File getProfilePicture() {
        return profilePicture != null ? profilePicture : getDefaultProfilePicture();
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture != null ? profilePicture : getDefaultProfilePicture();
    }

    private File getDefaultProfilePicture() {
        return new File("path/to/default/profilePicture.png");
    }

    public List<MedicalTitle> getMedicalTitles() {
        return medicalTitles;
    }

    public void setMedicalTitles(List<MedicalTitle> medicalTitles) {
        Assertion.isNotNull(medicalTitles, "medicalTitles");
        Assertion.isNotEmpty(medicalTitles, "medicalTitles");
        this.medicalTitles = medicalTitles;
    }

    public List<Hashtag> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Hashtag> experiences) {
        Assertion.isNotNull(experiences, "experiences");
        Assertion.isNotEmpty(experiences, "experiences");
        this.experiences = experiences;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        Assertion.isNotNull(languages, "languages");
        Assertion.isNotEmpty(languages, "languages");
        this.languages = languages;
    }

    public Integer getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(Integer activityScore) {
        Assertion.isNotNull(activityScore, "activityScore");
        Assertion.isTrue(activityScore >= 0, () -> "Activity score must be a positive integer");
        Assertion.isTrue(activityScore <= 100, () -> "Activity score must be below 100");
        this.activityScore = activityScore;
    }

    public Integer getExpertScore() {
        return expertScore;
    }

    public void setExpertScore(Integer expertScore) {
        this.expertScore = expertScore;
    }

    public Double getAverageExpertScore() {
        return correctAnswerPercentages.isEmpty() ? 0.0 : correctAnswerPercentages.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public void addExpertScore(int percentage, boolean correct) {
        if (correct) {
            correctAnswerPercentages.add(percentage);
            expertScore += percentage;
        }
    }

    public void addActivityScore(Integer activityScoreToAdd) {
        this.activityScore += activityScoreToAdd;
    }
}