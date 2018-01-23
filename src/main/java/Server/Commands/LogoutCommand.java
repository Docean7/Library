package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);
    @Override
    public String execute(RequestContent requestContent) {
        LOG.info("User " + requestContent.getAttribute("login") + " logged out");
        requestContent.deleteSession();
        return ConfigurationManager.getProperty("path.page.index");
    }
}
