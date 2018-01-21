package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;

import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(RequestContent requestContent, HttpServletResponse response) {
        requestContent.deleteSession();
        return ConfigurationManager.getProperty("path.page.index");
    }
}
