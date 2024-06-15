package com.fivepanels.application.model.domain.user;

import com.fivepanels.application.model.domain.messenger.Chat;
import com.fivepanels.application.model.domain.messenger.Messenger;
import com.fivepanels.application.model.foundation.BaseEntity;
import com.fivepanels.application.model.domain.medicalcase.MedicalCase;
import com.fivepanels.application.model.domain.user.misc.Email;
import com.fivepanels.application.model.domain.user.misc.Password;
import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.UserException;
import com.fivepanels.application.model.repository.UserRepository;
import com.fivepanels.application.model.domain.user.misc.Hashtag;

import java.io.File;
import java.util.*;

import static com.fivepanels.application.model.domain.user.UserRelationship.ESTABLISHED;

public class User extends BaseEntity {

    private Email email;
    private Password password;
    private Messenger messenger;
    private Set<MedicalCase> isMemberOfMedicalCases;
    private Set<MedicalCase> isOwnerOfMedicalCases;
    private UserProfile userProfile;
    private Map<UUID, UserRelationship> relationships;

    public User(String firstName, String lastName, String city, Email email, Password password) {
        super();
        setEmail(email);
        setPassword(password);
        this.userProfile = new UserProfile(firstName, lastName, city);
        this.messenger = new Messenger();
        this.isMemberOfMedicalCases = new HashSet<>();
        this.isOwnerOfMedicalCases = new HashSet<>();
        this.relationships = new HashMap<>();
        UserRepository.save(this);
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        Assertion.isNotNull(email, "email");
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        Assertion.isNotNull(password, "password");
        this.password = password;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public Set<MedicalCase> getIsMemberOfMedicalCases() {
        return isMemberOfMedicalCases;
    }

    public Set<MedicalCase> getIsOwnerOfMedicalCases() {
        return isOwnerOfMedicalCases;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        Assertion.isNotNull(userProfile, "userProfile");
        this.userProfile = userProfile;
    }

    public Map<UUID, UserRelationship> getRelationships() {
        return relationships;
    }

    public UserRelationship getUserRelationship(UUID userId) {
        return relationships.get(userId);
    }

    public void setUserRelationship(UUID userId, UserRelationship relationship) {
        relationships.put(userId, relationship);
    }

    public MedicalCase createNewMedicalCase(String medicalCaseName, List<String> textContent, List<File> fileContent, Set<User> medicalCaseMembers, Set<Hashtag> medicalCaseHashtags) {
        Assertion.isNotNull(medicalCaseName, "medicalCaseName");
        Assertion.isNotBlank(medicalCaseName, "medicalCaseName");
        Assertion.isNotNull(textContent, "textContent");
        Assertion.hasMinLength(medicalCaseName, 8, "medicalCaseName");
        Assertion.hasMaxLength(medicalCaseName, 128, "medicalCaseName");

        MedicalCase mc = new MedicalCase(medicalCaseName, this, textContent, fileContent, medicalCaseMembers, medicalCaseHashtags);
        this.isOwnerOfMedicalCases.add(mc);
        this.userProfile.addActivityScore(40);
        UserRepository.save(this);
        return mc;
    }

    public void deleteMedicalCase(MedicalCase medicalCase) {
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (!isOwnerOfMedicalCases.contains(medicalCase)) {
            throw new UserException("User is not an owner of medical case");
        }
        isOwnerOfMedicalCases.remove(medicalCase);
        UserRepository.deleteById(medicalCase.getId());
    }

    public void joinMedicalCase(MedicalCase medicalCase) {
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (this.isMemberOfMedicalCases.contains(medicalCase)) {
            throw new UserException("User is already a member of medical case");
        }
        this.isMemberOfMedicalCases.add(medicalCase);
        this.userProfile.addActivityScore(5);
    }

    public void leaveMedicalCase(MedicalCase medicalCase) {
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (!this.isMemberOfMedicalCases.contains(medicalCase)) {
            throw new UserException("User is not a member of medical case");
        }
        this.isMemberOfMedicalCases.remove(medicalCase);
    }

    public void addMemberToMedicalCase(User userToAdd, MedicalCase medicalCase) {
        Assertion.isNotNull(userToAdd, "userToAdd");
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (medicalCase.getMedicalCaseMembers().contains(userToAdd)) {
            throw new UserException("User is already a member of medical case");
        }
        if (this.getId().equals(userToAdd.getId())) {
            throw new UserException("User cannot add himself to medical case");
        }
        medicalCase.getMedicalCaseMembers().add(userToAdd);
        userToAdd.getIsMemberOfMedicalCases().add(medicalCase);
    }

    public void removeMemberFromMedicalCase(User userToRemove, MedicalCase medicalCase) {
        Assertion.isNotNull(userToRemove, "userToRemove");
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (!medicalCase.getMedicalCaseMembers().contains(userToRemove)) {
            throw new UserException("User is not a member of medical case");
        }
        medicalCase.getMedicalCaseMembers().remove(userToRemove);
        userToRemove.getIsMemberOfMedicalCases().remove(medicalCase);
    }

    public void addFriend(User friend) {
        Assertion.isNotNull(friend, "friend");
        if (friend.equals(this)) {
            throw new UserException("Cannot add yourself as a friend");
        }

        if (this.relationships.containsKey(friend.getId()) && this.relationships.get(friend.getId()) == ESTABLISHED) {
            throw new UserException("You are already friends with this user");
        }

        if (this.relationships.containsKey(friend.getId()) && this.relationships.get(friend.getId()) == UserRelationship.OUTGOING) {
            throw new UserException("Friend request already sent to this user");
        }

        this.relationships.put(friend.getId(), UserRelationship.OUTGOING);
        friend.getRelationships().put(this.getId(), UserRelationship.INCOMING);
    }

    public void removeFriend(User friend) {
        Assertion.isNotNull(friend, "friend");

        // Remove relationship
        this.relationships.remove(friend.getId());
        friend.getRelationships().remove(this.getId());

        // Find and remove the chat
        Chat chat = this.messenger.getChats().stream()
                .filter(c -> c.getMembers().contains(this) && c.getMembers().contains(friend))
                .findFirst()
                .orElse(null);

        if (chat != null) {
            this.messenger.removeChat(chat);
            friend.getMessenger().removeChat(chat);
        }
    }

    public void acceptFriendRequest(User friend) {
        Assertion.isNotNull(friend, "friend");
        if (this.relationships.get(friend.getId()) == UserRelationship.INCOMING &&
                friend.getRelationships().get(this.getId()) == UserRelationship.OUTGOING) {
            this.relationships.put(friend.getId(), ESTABLISHED);
            friend.getRelationships().put(this.getId(), ESTABLISHED);
            createDirectChat(friend);
        } else {
            throw new UserException("Friend request not found");
        }
    }

    public void declineFriendRequest(User friend) {
        Assertion.isNotNull(friend, "friend");
        if (this.relationships.get(friend.getId()) == UserRelationship.INCOMING) {
            this.relationships.remove(friend.getId());
            friend.getRelationships().remove(this.getId());
        } else {
            throw new UserException("Friend request not found");
        }
    }

    public void createDirectChat(User friend) {
        Set<User> members = new HashSet<>();
        members.add(this);
        members.add(friend);
        Chat chat = new Chat("Direct Chat between " + this + " and " + friend + "!", members);
        this.getMessenger().addChat(chat);
        friend.getMessenger().addChat(chat);
    }

    public void createGroupChatWith(User... friends) {
        Assertion.isNotNull(friends, "friends");
        Assertion.isNotEmpty(friends, "friends");
        if (Arrays.stream(friends).count() < 3 || Arrays.stream(friends).count() > 20) {
            throw new UserException("Group chat must have between 3 and 20 members");
        }
        Set<User> members = new HashSet<>();
        members.add(this);
        Collections.addAll(members, friends);
        Chat chat = new Chat("Group Chat", members);
        this.getMessenger().addChat(chat);
        for (User friend : friends) {
            friend.getMessenger().addChat(chat);
        }
    }

    public void addAnswerToMedicalCase(MedicalCase medicalCase, String answerText, boolean isCorrect) {
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (!this.equals(medicalCase.getOwner())) {
            throw new UserException("Only the owner can add answers to the medical case");
        }
        medicalCase.addAnswer(answerText, isCorrect);
    }

    public void voteOnMedicalCase(MedicalCase medicalCase, UUID answerId, int percentage) {
        Assertion.isNotNull(medicalCase, "medicalCase");
        if (!medicalCase.getMedicalCaseMembers().contains(this)) {
            throw new UserException("Only members can vote on the medical case");
        }
        // Validate total votes for the user
        if (medicalCase.getTotalVotePercentageForUser(this) + percentage > 100) {
            throw new UserException("Total voting percentage exceeds 100%");
        }
        medicalCase.vote(this, answerId, percentage);
        this.userProfile.addActivityScore(10);
    }

    public double getAverageExpertScore() {
        return this.userProfile.getAverageExpertScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return userProfile.getFirstName() + " " + userProfile.getLastName();
    }
}