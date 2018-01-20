package Server.Commands;

import db.DBManager;

public class RegistrationLogic {
    private static DBManager dbManager = DBManager.getInstance();
    public static boolean isAvailableLogin(String login){
        return dbManager.getUserByLogin(login) == null ;
    }

    public static boolean isAvailableEmail(String email){
      return dbManager.checkEmail(email);
    }

}
