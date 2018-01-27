package Server.Commands;

import db.DBManager;
import exception.DBException;

public class RegistrationLogic {
    private static DBManager dbManager = DBManager.getInstance();
    public static boolean isAvailableLogin(String login) throws DBException{
        return dbManager.getUserByLogin(login) == null ;
    }

    public static boolean isAvailableEmail(String email) throws DBException{
      return dbManager.checkEmail(email);
    }



}
