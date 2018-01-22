package Server.Commands;

import db.BCrypt;
import db.DBManager;
import db.Entity.User;
import db.UserTypeEnum;

public class LoginLogic {
    private static final int ALL_GOOD = 0;
    private static final int WRONG_LOGIN = 1;
    private static final int WRONG_PASSWORD = 2;

    public static int checkLogin(String login, String password) {

        User user = DBManager.getInstance().getUserByLogin(login);
        if (user == null || user.getUserType() == UserTypeEnum.BANNED_USER.value()) {
            return WRONG_LOGIN;
        } else if(!BCrypt.checkpw(password, user.getPassword())){
                return WRONG_PASSWORD;
            }
        else return ALL_GOOD;
    }
}
