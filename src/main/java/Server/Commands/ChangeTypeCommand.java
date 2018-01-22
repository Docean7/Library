package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;

public class ChangeTypeCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        int type = Integer.parseInt(requestContent.getParameter("type"));
        DBManager.getInstance().changeUserType(type,requestContent.getParameter("username"));
        return page;
    }
}
