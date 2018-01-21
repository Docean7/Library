package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        requestContent.deleteSession();
        return ConfigurationManager.getProperty("path.page.index");
    }
}
