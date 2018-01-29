package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class.getName());
    @Override
    public String execute(RequestContent requestContent) throws AppException {
        LOG.debug("User " + requestContent.getSessionAttribute("login") + " logged out");
        requestContent.deleteSession();
        return ConfigurationManager.getProperty("path.page.index");
    }
}
