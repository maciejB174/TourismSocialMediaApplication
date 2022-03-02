package mtu.tourismSocialMediaApplication;

import mtu.tourismSocialMediaApplication.Objects.User;

public class LoggedUser {

    private static LoggedUser ourInstance = new LoggedUser();

    private User loggedUser;

    public void clear() {
        ourInstance = new LoggedUser();
    }

    public static LoggedUser getInstance () {
        return ourInstance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
