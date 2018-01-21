package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
