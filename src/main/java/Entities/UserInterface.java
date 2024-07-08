package Entities;

import java.util.HashSet;

public interface UserInterface {

    int getUserId();
    String getFirstName();
    String getLastName();
    String getUserEmail();
    HashSet<String> getTags();
    double getDesiredCompensation();

    void setUserId(int userId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setUserEmail(String userEmail);
    void setTags(HashSet<String> tags);
    void setDesiredCompensation(double desiredCompensation);
}
