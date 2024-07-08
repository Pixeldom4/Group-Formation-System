package Entities;

import java.util.HashSet;

public interface UserInterface {

    public int getUserId();
    public String getFirstName();
    public String getLastName();
    public String getUserEmail();
    public HashSet<String> getTags();
    public double getDesiredCompensation();

    public void setUserId(int userId);
    public void setFirstName(String firstName);
    public void setLastName(String lastName);
    public void setUserEmail(String userEmail);
    public void setTags(HashSet<String> tags);
    public void setDesiredCompensation(double desiredCompensation);
}
