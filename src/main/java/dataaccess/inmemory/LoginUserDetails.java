package dataaccess.inmemory;

import dataaccess.ILoginUserDetails;

import java.util.HashSet;

public class LoginUserDetails implements ILoginUserDetails {
    private int userId;
    private boolean isLoggedIn;

    public LoginUserDetails(){
        this.isLoggedIn = false;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    @Override
    public void logout() {
        userId = 0;
        this.isLoggedIn = false;
    }

    @Override
    public void login(int userId) {
        this.userId = userId;
        this.isLoggedIn = true;
    }
}
