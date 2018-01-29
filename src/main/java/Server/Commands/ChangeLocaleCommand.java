package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.UserTypeEnum;
import exception.AppException;

public class ChangeLocaleCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.account");
        int userType = Integer.parseInt(String.valueOf(requestContent.getSessionAttribute("userType")));
        if(userType == UserTypeEnum.LIBRARIAN.value()){
            page = ConfigurationManager.getProperty("path.page.librarian.acc");
        }
        else if (userType == UserTypeEnum.ADMIN.value()){
            page = ConfigurationManager.getProperty("path.page.admin.acc");
        }
        String locale = requestContent.getParameter("locale");
        requestContent.addSessionAttribute("locale", locale);
        return page;
    }
}
