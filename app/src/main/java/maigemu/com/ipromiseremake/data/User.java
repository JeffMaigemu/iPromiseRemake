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

}