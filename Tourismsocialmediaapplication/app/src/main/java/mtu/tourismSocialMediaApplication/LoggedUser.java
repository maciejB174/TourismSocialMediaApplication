package mtu.tourismSocialMediaApplication;

import mtu.tourismSocialMediaApplication.Objects.User;

public class LoggedUser {

    private static LoggedUser ourInstance = new LoggedUser();

    private String email;

    public void clear() {
        ourInstance = new LoggedUser();
    }

    public static LoggedUser getInstance () {
        return ourInstance;
    }

    public String getLoggedUser() {
        return email;
    }

    public void setLoggedUser(String loggedUser) {
        this.email = loggedUser;
    }
}
