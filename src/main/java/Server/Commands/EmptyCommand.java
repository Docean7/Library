package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import exception.AppException;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
