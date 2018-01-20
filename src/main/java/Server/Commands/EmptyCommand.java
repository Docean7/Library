package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;

import javax.servlet.http.HttpServletResponse;

public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
