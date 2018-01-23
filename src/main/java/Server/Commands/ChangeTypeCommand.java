package Server.Commands;

import Server.Managers.ConfigurationManager;
import Server.Managers.RequestContent;
import db.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeTypeCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(ChangeTypeCommand.class);
    @Override
    public String execute(RequestContent requestContent) {
        String page = ConfigurationManager.getProperty("path.page.admin.acc");
        String username = requestContent.getParameter("username");
        int type = Integer.parseInt(requestContent.getParameter("type"));
        DBManager.getInstance().changeUserType(type, username);
        LOG.info("Changing usertype of " + username + " to " + type);
        return page;
    }
}
