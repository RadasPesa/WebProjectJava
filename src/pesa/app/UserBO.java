package pesa.app;

import pesa.db.DBO;
import pesa.db.model.WebUserEntity;

import java.util.List;

public class UserBO {

    public static boolean addNewUser(WebUserEntity user) {
        boolean ret = false;

        List<WebUserEntity> users = getRegisteredUsers();

        boolean exists = false;

        if (users != null) {
            for (WebUserEntity u : users) {
                if (user.getNickname().toLowerCase().equals(u.getNickname().toLowerCase())) {
                    exists = true;
                    break;
                }
            }
        }

        if (!exists) {
            ret = DBO.addNewUser(user);
        }

        return ret;
    }

    public static List<WebUserEntity> getRegisteredUsers() {
        return DBO.getRegisteredUsers();
    }

    public static WebUserEntity checkLoginData(WebUserEntity user) {
        WebUserEntity ret = null;

        List<WebUserEntity> users = getRegisteredUsers();

        for (WebUserEntity u : users) {
            if (user.getNickname().toLowerCase().equals(u.getNickname().toLowerCase()) && user.getPassword().equals(u.getPassword())) {
                ret = u;
            }
        }

        return ret;
    }

    public static int getUserRole(WebUserEntity user) {
        int ret;

        ret = DBO.getUserRole(user.getUserId());

        return ret;
    }

    public static WebUserEntity getUser(String managedUsername) {
        return DBO.getUser(managedUsername);
    }

    public static WebUserEntity getUser(int userId) {
        return DBO.getUser(userId);
    }

    public static boolean changeUsername(WebUserEntity user, String newUsername) {
        boolean changed = false;

        List<WebUserEntity> users = getRegisteredUsers();

        boolean exists = false;
        for (WebUserEntity u : users) {
            if (u.getNickname().toLowerCase().equals(newUsername.toLowerCase())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            changed = DBO.changeUsername(user, newUsername);
        }

        return changed;
    }

    public static boolean changePassword(WebUserEntity user, String newPassword) {
        return DBO.changePassword(user, newPassword);
    }
}
