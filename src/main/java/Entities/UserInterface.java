package Entities;

import java.util.ArrayList;

public interface UserInterface {

    public int getUserId();
    public String getUserName();
    public String getUserEmail();
    public ArrayList<String> getSkills();
    public double getDesiredCompensation();

    public void setUserId(int userId);
    public void setUserName(String userName);
    public void setUserEmail(String userEmail);
    public void setSkills(ArrayList<String> skills);
    public void setDesiredCompensation(double desiredCompensation);
}
