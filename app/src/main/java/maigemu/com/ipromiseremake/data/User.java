package maigemu.com.ipromiseremake.data;

/**
 * Created by JEFFREY on 18/03/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String email;
    public String firstName;
    public String lastName;
    public String phone;
    public String occupation;
    public String LGA;
    public String indegeneStatus;
    public String maritalStatus;
    public String ageCategory;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String email, String firstName, String lastName, String phone, String occupation,
                String LGA, String indegeneStatus, String maritalStatus, String ageCategory) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.occupation = occupation;
        this.LGA = LGA;

        this.indegeneStatus = indegeneStatus;
        this.maritalStatus = maritalStatus;
        this.ageCategory = ageCategory;

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLGA() {
        return LGA;
    }

    public void setLGA(String LGA) {
        this.LGA = LGA;
    }

    public String getIndegeneStatus() {
        return indegeneStatus;
    }

    public void setIndegeneStatus(String indegeneStatus) {
        this.indegeneStatus = indegeneStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}